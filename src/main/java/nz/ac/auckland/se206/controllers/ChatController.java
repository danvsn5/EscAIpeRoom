package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionManager.MISSION;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

/** Controller class for the chat view. */
public class ChatController {
  public static ChatMessage gptMessage;
  private boolean isGenerating = false;

  @FXML private TextArea chatTextArea;
  @FXML private TextField inputText;
  @FXML private Button sendButton;
  @FXML private Button hintButton;
  @FXML private Label counter;
  @FXML private Circle loadingCircle;
  @FXML private Label listeningLabel;
  @FXML private ProgressIndicator loading;
  @FXML private ImageView progressButton;
  @FXML private ImageView fuel;
  @FXML private ImageView sand;
  @FXML private ImageView treeListening;
  @FXML private ImageView treeTalking;
  @FXML private ImageView treeThinking;
  @FXML private ImageView rootInitial;
  @FXML private ImageView rootOne;
  @FXML private ImageView rootTwo;
  @FXML private ImageView rootThree;
  @FXML private Rectangle hintRectangle;
  @FXML private Label hintNumber;
  @FXML private Label chatLabel;
  @FXML private ImageView smallBubble;
  @FXML private ImageView largeBubble;
  @FXML private ImageView medBubble;
  @FXML private ImageView notebook;
  @FXML private ImageView zoomBook;
  @FXML private Button closeBookButton;
  @FXML private Polygon notebookCollisionBox;
  private int bubbleVariable = 0;
  private int bookVariable = 0;
  private ChatMessage thinkingMessage =
      new ChatMessage("Wise Mystical Tree", "Allow me to ponder...");
  private ChatMessage activationMessage =
      new ChatMessage("Wise Mystical Tree", "That is good to hear... Allow me to ponder...");

  public static ChatCompletionRequest chatCompletionRequest;
  public static ChatMessage firstMesage;
  public static int seenFirstMessage = 0;
  public static ChatMessage secondGuideMessage;
  Timeline bubbleTimeline =
      new Timeline(new KeyFrame(javafx.util.Duration.millis(333), e -> thinkBubble()));
  private int firstMission;
  private int secondMission;

  /**
   * Initializes the chat view, loading the riddle.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
    bubbleTimeline.setCycleCount(Timeline.INDEFINITE);

    chatTextArea.setEditable(false); // prevents user from editing the chat text area

    inputText.setDisable(true);
    inputText.setStyle("-fx-background-color: transparent;");
    // Start thinking
    startThink();

    loading.setVisible(true);
    loadingCircle.setFill(Color.LIGHTGRAY);

    String mission1;

    if (GameState.missionListA.contains(1)) {
      mission1 =
          "Know how to fix the window? I shall give you a riddle and the answer shuold guide you"
              + " to the next step. Are you ready? \n\n";
      chatTextArea.appendText(mission1);
      chatLabel.setText(mission1);
      System.out.println("chatLineCode");

    } else if (GameState.missionListA.contains(2)) {
      mission1 =
          "Know how to charge the fuel? I shall give you a riddle and the answer shuold guide"
              + " you to the next step. Are you ready? \n\n";
      chatTextArea.appendText(mission1);
      System.out.println("chatLineCode");
      chatLabel.setText(mission1);
    }

    Task<Void> introCall =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            isGenerating = true;
            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.7)
                    .setTopP(0.7)
                    .setMaxTokens(100);
            // runs the initial gpt compulsory message for working. message does not get appended to
            // the chat box and TTS is NOT applied to this message.
            gptMessage = runGpt(new ChatMessage("user", GptPromptEngineering.introCall()));

            /* ================================== MESSAGE DOES NOT GET APPENDED ================================= */
            // appendChatMessage(gptMessage);

            return null;
          }
        };
    loading.progressProperty().bind(introCall.progressProperty());

    introCall.setOnSucceeded(
        e -> {
          isGenerating = false;
          smallBubble.setVisible(false);
          medBubble.setVisible(false);
          largeBubble.setVisible(false);
          bubbleTimeline.pause();
          System.out.println("timeline should have stopped");
          bubbleVariable = 0;
          // End thinking, start talking
          loading.progressProperty().unbind();
          loading.setVisible(false);
          loadingCircle.setFill(Color.valueOf("264f31"));
          inputText.setDisable(false);
          startTalk();
        });

    Thread mainRiddleThread = new Thread(introCall);
    mainRiddleThread.start();
  }

  public ChatCompletionRequest getChatCompletionRequest() {
    return chatCompletionRequest;
  }

  public void setChatCompletionRequest(ChatCompletionRequest chatCompletionRequest) {
    this.chatCompletionRequest = chatCompletionRequest;
  }

  public void goProgress() {
    SceneManager.setPrevious(AppPanel.CHAT);
    App.setUi(AppPanel.PROGRESS);
  }

  /**
   * Appends a chat message to the chat text area.
   *
   * @param msg the chat message to append
   */
  private void appendChatMessage(ChatMessage msg) {
    //   chatTextArea.appendText(msg.getRole() + ": " + msg.getContent() + "\n\n");
    chatLabel.setText(msg.getContent());
  }

  private void appendChatMessageArea(ChatMessage msg) {
    chatTextArea.appendText(msg.getRole() + ": " + msg.getContent() + "\n\n");
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   * @return the response chat message
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
    getChatCompletionRequest().addMessage(msg);
    try {
      ChatCompletionResult chatCompletionResult = getChatCompletionRequest().execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      chatCompletionRequest.addMessage(result.getChatMessage());
      result.getChatMessage().setRole("Wise Ancient Tree");
      // appendChatMessage(result.getChatMessage());
      result.getChatMessage().setRole("assistant");
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      ChatMessage error = new ChatMessage(null, null);

      error.setRole("Wise Mystical Tree");

      error.setContent(
          "Sorry, there was a problem generating a response. Please try restarting the"
              + " application.");
      appendChatMessage(error);
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Sends a message to the GPT model.
   *
   * @param event the action event triggered by the send button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onSendMessage(ActionEvent event) throws ApiProxyException, IOException {
    if (isGenerating) {
      return;
    } else if (inputText.getText().trim().isEmpty()) {
      return;
    }
    startThink();
    bubbleTimeline.play();
    inputText.setDisable(true);
    loading.setProgress(0);
    loading.setVisible(true);
    loadingCircle.setFill(Color.LIGHTGRAY);

    String message = inputText.getText();
    System.out.println(message);
    if (message.trim().isEmpty()) {
      inputText.setDisable(false);
      return;
    }
    inputText.clear();

    // Start listen
    loadingCircle.setFill(Color.LIGHTGRAY);
    loading.setProgress(0);
    loading.setVisible(true);
    // startListen();

    if (!GameState.isGreetingShown && !GameState.isFirstMissionCompleted) {
      generateFirstRiddle(message);
      GameState.isGreetingShown = true;
      listeningLabel.setVisible(false);
      if (GameState.getDifficulty() != 2) {
        hintButton.setVisible(true);
        hintButton.setDisable(false);
        System.out.println("1");
        if (GameState.getDifficulty() == 1) {
          hintNumber.setVisible(true);
          hintNumber.setDisable(false);
          hintRectangle.setVisible(true);
          hintRectangle.setDisable(false);
          System.out.println("2");
        }
      }
      return;
    }

    Task<Void> typeCall =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            isGenerating = true;

            ChatMessage msg = new ChatMessage("user", message);
            msg.setRole("Me");
            appendChatMessageArea(msg);
            System.out.println(msg.getContent());
            msg.setRole("user");
            // appendChatMessageArea(thinkingMessage);
            ChatMessage lastMsg = runGpt(msg);
            lastMsg.setRole("Wise Ancient Tree");
            Platform.runLater(() -> appendChatMessage(lastMsg));
            appendChatMessageArea(lastMsg);

            lastMsg.setRole("assistant");
            System.out.println("lastMsg");
            // if riddle was solved correctly, then -1 is added to the inventory; -2 is determined
            // from the launch panel and checks whether or not text to speech will be active

            if (!GameState.firstRiddleSolved) {
              System.out.println("first riddle not solved");
              // Check if the player gussess correctly
              if (lastMsg.getRole().equals("assistant")
                  && lastMsg.getContent().startsWith("Correct")) {
                if (!GameState.firstRiddleSolved && GameState.missionList.contains(2)) {
                  // If the player guesses correctly and its fuel mission, show the fuel and
                  // increase stage
                  GameState.missionManager.getMission(MISSION.FUEL).increaseStage();
                  GameState.progressBarGroup.updateProgressOne(MISSION.FUEL);
                  System.out.println("Fuel Mission 1 Complete");
                  fuel.setDisable(false);
                  fuel.setVisible(true);
                } else if (!GameState.firstRiddleSolved && GameState.missionList.contains(1)) {
                  // If the player guesses correctly and its window mission, show the sand and
                  // increase stage
                  GameState.missionManager.getMission(MISSION.WINDOW).increaseStage();
                  GameState.progressBarGroup.updateProgressOne(MISSION.WINDOW);
                  System.out.println("Window riddle solved");
                  sand.setDisable(false);
                  sand.setVisible(true);
                }
                GameState.firstRiddleSolved = true;

                // Speak the correct message from the tree
                GameState.speak(lastMsg.getContent());
                System.out.println("first riddle solved");
              }
            }

            //     else if (!GameState.firstRiddleSolved && GameState.missionList.contains(1)) {
            //       GameState.missionManager.getMission(MISSION.WINDOW).increaseStage();
            //       GameState.progressBarGroup.updateProgressOne(MISSION.WINDOW);
            //       System.out.println("Window riddle solved");
            //       sand.setDisable(false);
            //       sand.setVisible(true);
            //     }
            //     GameState.firstRiddleSolved = true;

            //     // Speak the correct message from the tree
            //     GameState.speak(lastMsg.getContent());
            //     System.out.println("first riddle solved");
            //   }
            // } else if (GameState.firstRiddleSolved && !GameState.secondRiddleSolved) {
            //   if (lastMsg.getRole().equals("assistant")
            //       && lastMsg.getContent().startsWith("Correct")) {
            //     GameState.secondRiddleSolved = true;
            //   }
            // }
            updateProgress(1, 1);
            return null;
          }
        };

    loading.progressProperty().bind(typeCall.progressProperty());

    typeCall.setOnSucceeded(
        e -> {
          isGenerating = false;
          // Start talk
          smallBubble.setVisible(false);
          medBubble.setVisible(false);
          largeBubble.setVisible(false);
          bubbleTimeline.pause();
          System.out.println("timeline should have stopped");
          bubbleVariable = 0;
          loading.progressProperty().unbind();
          loading.setVisible(false);
          loadingCircle.setFill(Color.valueOf("264f31"));
          inputText.setDisable(false);
          startTalk();
        });

    Thread typeInThread = new Thread(typeCall);
    typeInThread.start();
  }

  /**
   * Navigates back to the previous view.
   *
   * @param event the action event triggered by the go back button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onGoBack(ActionEvent event) throws ApiProxyException, IOException {
    startListen();
    if (SceneManager.getPrevious() == AppPanel.CHAT) {
      SceneManager.setPrevious(AppPanel.OUTSIDE);
    }
    App.setUi(SceneManager.getPrevious());
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   * @throws IOException
   * @throws ApiProxyException
   */
  @FXML
  public void onKeyPressed(KeyEvent event) throws ApiProxyException, IOException {
    inputText.setStyle("-fx-background-color: transparent;");

    startListen();
    treeThinking.setVisible(true);
    if (event.getCode().toString().equals("ENTER")) {
      onSendMessage(new ActionEvent());
    }
    inputText.setStyle("-fx-background-color: transparent;");
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    inputText.setStyle("-fx-background-color: transparent;");

    System.out.println("key " + event.getCode() + " released");
    if (inputText.getText().trim().isEmpty()) {
      startTalk();
      listeningLabel.setVisible(false);
    }
    inputText.setStyle("-fx-background-color: transparent;");
  }

  public void activateProgressGlow() {
    progressButton.setEffect(GameState.glowBright);
  }

  public void deactivateProgressGlow() {
    progressButton.setEffect(GameState.glowDim);
  }

  private void generateFirstRiddle(String message) {

    inputText.setDisable(true);
    startThink();
    loading.setVisible(true);
    loadingCircle.setFill(Color.LIGHTGRAY);

    System.out.println("generate riddle");

    for (int i = 0; i < 1; i++) {
      System.out.println("----");
      if (GameState.missionList.get(i) == 1 || GameState.missionList.get(i) == 2) {
        System.out.println("first mission");
        firstMission = GameState.missionList.get(i);
        System.out.println(firstMission);
      } else {
        System.out.println("second mission");
        secondMission = GameState.missionList.get(i);
        System.out.println(secondMission);
      }
    }

    Task<Void> firstRiddleTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            isGenerating = true;

            ChatMessage msg = new ChatMessage("user", message);

            msg.setRole("Me");
            appendChatMessageArea(msg);
            msg.setRole("user");
            // appendChatMessageArea(activationMessage);

            setChatCompletionRequest(
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.5)
                    .setTopP(0.2)
                    .setMaxTokens(150));

            System.out.println("first mission riddle");
            if (firstMission == 1) { // if the first mission is the window
              gptMessage =
                  runGpt(
                      new ChatMessage(
                          "user", GptPromptEngineering.getRiddleWithGivenWordWindow("sand")));
              gptMessage.setRole("Wise Ancient Tree");
              Platform.runLater(() -> appendChatMessage(gptMessage));
              appendChatMessageArea(gptMessage);

              gptMessage.setRole("assistant");
            } else if (firstMission == 2) { // if it is the fuel
              gptMessage =
                  runGpt(
                      new ChatMessage(
                          "user", GptPromptEngineering.getRiddleWithGivenWordWindow("sky")));
              gptMessage.setRole("Wise Ancient Tree");
              Platform.runLater(() -> appendChatMessage(gptMessage));
              appendChatMessageArea(gptMessage);
              gptMessage.setRole("assistant");
            }

            updateProgress(1, 1);
            return null;
          }
        };

    loading.progressProperty().bind(firstRiddleTask.progressProperty());

    firstRiddleTask.setOnSucceeded(
        e2 -> {
          isGenerating = false;
          smallBubble.setVisible(false);
          medBubble.setVisible(false);
          largeBubble.setVisible(false);
          bubbleTimeline.pause();
          System.out.println("timeline should have stopped");
          bubbleVariable = 0;
          loading.progressProperty().unbind();
          startTalk();
          loading.setVisible(false);
          loadingCircle.setFill(Color.valueOf("264f31"));
          inputText.setDisable(false);
          treeThinking.setVisible(false);
          treeTalking.setVisible(true);
        });

    Thread firstRiddleThread = new Thread(firstRiddleTask);
    firstRiddleThread.start();
  }

  public void fuelLight() {
    fuel.setEffect(GameState.glowBright);
  }

  public void fuelNeutral() {
    fuel.setEffect(GameState.glowDim);
  }

  public void collectFuel() {
    GameState.inventory.add(8); // fuel collected
    GameState.missionManager.getMission(MISSION.FUEL).increaseStage();
    GameState.progressBarGroup.updateProgressOne(MISSION.FUEL);
    System.out.println("Fuel Mission 2 Complete");
    fuel.setVisible(false);
    fuel.setDisable(true);
    SceneManager.showDialog("Info", "Fuel Collected", "A heavy fuel tank");
  }

  public void activateSandGlow() {
    sand.setEffect(GameState.glowBright);
  }

  public void deactivateSandGlow() {
    sand.setEffect(GameState.glowDim);
  }

  public void collectSand() {
    GameState.missionManager.getMission(MISSION.WINDOW).increaseStage();
    GameState.progressBarGroup.updateProgressOne(MISSION.WINDOW);
    System.out.println("Collected sand");
    sand.setVisible(false);
    sand.setDisable(true);
    GameState.inventory.add(2);
    SceneManager.showDialog(
        "Info", "Sand Collected", "A pile of sand which can be melted into glass");
  }

  private void startListen() {
    treeListening.setVisible(true);
    treeTalking.setVisible(false);
    treeThinking.setVisible(false);
  }

  private void startTalk() {
    treeListening.setVisible(false);
    treeTalking.setVisible(true);
    treeThinking.setVisible(false);
  }

  private void startThink() {
    treeListening.setVisible(false);
    treeTalking.setVisible(false);
    treeThinking.setVisible(true);
  }

  private void generatePuzzle(String message) {
    inputText.setDisable(true);

    startThink();

    loading.setVisible(true);
    loadingCircle.setFill(Color.LIGHTGRAY);

    System.out.println("generate puzzle");

    //   Task<Void> secondPuzzleTask =
    //       new Task<Void>() {

    //         @Override
    //         protected Void call() throws Exception {

    //           ChatMessage msg = new ChatMessage("user", message);
    //           appendChatMessage(msg);

    //           setChatCompletionRequest(
    //               new ChatCompletionRequest()
    //                   .setN(1)
    //                   .setTemperature(0.5)
    //                   .setTopP(0.2)
    //                   .setMaxTokens(100));

    //           System.out.println("first mission riddle");
    //           if (firstMission == 3) { // if the first mission is the controller
    //             gptMessage =
    //                 runGpt(new ChatMessage("user", GptPromptEngineering.getControllerPuzzle()));
    //             gptMessage.setRole("Wise Ancient Tree");
    //             appendChatMessage(gptMessage);
    //             gptMessage.setRole("assistant");
    //           } else if (firstMission == 4) { // if it is the thruster
    //             if (GameState.randomColorNumber == 1) { // red
    //               gptMessage =
    //                   runGpt(new ChatMessage("user",
    // GptPromptEngineering.getThrusterPuzzle("red")));
    //               gptMessage.setRole("Wise Ancient Tree");
    //               appendChatMessage(gptMessage);
    //               gptMessage.setRole("assistant");
    //             } else if (GameState.randomColorNumber == 2) { // blue
    //               gptMessage =
    //                   runGpt(new ChatMessage("user",
    // GptPromptEngineering.getThrusterPuzzle("blue")));
    //               gptMessage.setRole("Wise Ancient Tree");
    //               appendChatMessage(gptMessage);
    //               gptMessage.setRole("assistant");
    //             } else if (GameState.randomColorNumber == 3) { // green
    //               gptMessage =
    //                   runGpt(
    //                       new ChatMessage("user",
    // GptPromptEngineering.getThrusterPuzzle("green")));
    //               gptMessage.setRole("Wise Ancient Tree");
    //               appendChatMessage(gptMessage);
    //               gptMessage.setRole("assistant");
    //             } else if (GameState.randomColorNumber == 4) { // purple
    //               gptMessage =
    //                   runGpt(
    //                       new ChatMessage("user",
    // GptPromptEngineering.getThrusterPuzzle("purple")));
    //               gptMessage.setRole("Wise Ancient Tree");
    //               appendChatMessage(gptMessage);
    //               gptMessage.setRole("assistant");
    //             }
    //           }

    //           updateProgress(1, 1);
    //           return null;
    //         }
    //       };

    //   loading.progressProperty().bind(secondPuzzleTask.progressProperty());

    //   secondPuzzleTask.setOnSucceeded(
    //       e2 -> {
    //         loading.progressProperty().unbind();
    //         loading.setVisible(false);
    //         loadingCircle.setFill(Color.valueOf("264f31"));
    //         inputText.setDisable(false);
    //         startTalk();
    //       });

    //   Thread secondPuzzleThread = new Thread(secondPuzzleTask);
    //   secondPuzzleThread.start();
  }

  @FXML
  private void getHint(ActionEvent event) throws ApiProxyException, IOException {
    if (GameState.hintUsedUp()) {
      SceneManager.showDialog("Info", "Hint number used up", "No more hint allowed");
      return;
    }
    if (isGenerating) {
      SceneManager.showDialog("Info", "Tree is thinking, don't interrupt him", "Quiet!");
      return;
    }
    if (!GameState.isFirstMissionCompleted) {
      if (GameState.missionList.contains(1)) {
        System.out.println("Window hint");
        askByStage(MISSION.WINDOW);
      } else {
        System.out.println("Fuel hint");
        askByStage(MISSION.FUEL);
      }
    } else {
      if (GameState.missionList.contains(3)) {
        System.out.println("Controller hint");
        askByStage(MISSION.CONTROLLER);
      } else {
        System.out.println("Thruster hint");
        askByStage(MISSION.THRUSTER);
      }
    }
    GameState.useHint();
  }

  private void askByStage(MISSION missionType) {
    // Start thinking
    inputText.setDisable(true);
    startThink();
    loading.setVisible(true);
    loadingCircle.setFill(Color.LIGHTGRAY);

    Task<Void> hintTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            isGenerating = true;

            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.7)
                    .setTopP(0.7)
                    .setMaxTokens(150);

            gptMessage = runGpt(new ChatMessage("user", GptPromptEngineering.getHint(missionType)));
            gptMessage.setRole("Wise Ancient Tree");
            appendChatMessage(gptMessage);
            appendChatMessageArea(gptMessage);
            gptMessage.setRole("assistant");

            updateProgress(1, 1);
            return null;
          }
        };

    hintTask.setOnSucceeded(
        e -> {
          isGenerating = false;
          smallBubble.setVisible(false);
          medBubble.setVisible(false);
          largeBubble.setVisible(false);
          bubbleTimeline.pause();
          System.out.println("timeline should have stopped");
          bubbleVariable = 0;
          // End thinking, start talking
          loading.progressProperty().unbind();
          loading.setVisible(false);
          loadingCircle.setFill(Color.valueOf("264f31"));
          inputText.setDisable(false);
          startTalk();
        });

    Thread hintThread = new Thread(hintTask);
    hintThread.start();
  }

  public void thinkBubble() {

    // create switch case for bubble variable given 7 different states
    // 0 = no bubble
    // 1 = small bubble
    // 2 = medium bubble
    // 3 = large bubble
    // 4 = medium bubble
    // 5 = small bubble
    // 6 = no bubble
    switch (bubbleVariable) {
      case 0:
        smallBubble.setVisible(true);
        bubbleVariable = 1;
        break;
      case 1:
        medBubble.setVisible(true);
        bubbleVariable = 2;
        break;
      case 2:
        largeBubble.setVisible(true);
        bubbleVariable = 3;
        break;
      case 3:
        largeBubble.setVisible(false);
        bubbleVariable = 4;
        break;
      case 4:
        medBubble.setVisible(false);
        bubbleVariable = 5;
        break;
      case 5:
        smallBubble.setVisible(false);
        bubbleVariable = 0;
        break;
    }
  }

  public void openBook() {
    zoomBook.setVisible(true);
    bookVariable = 1;
    notebookCollisionBox.setOpacity(0);
    chatTextArea.setVisible(true);
    closeBookButton.setVisible(true);
  }

  public void closeBook() {
    zoomBook.setVisible(false);
    chatTextArea.setVisible(false);
    closeBookButton.setVisible(false);
    bookVariable = 0;
  }

  public void activateNotebookGlow() {
    if (bookVariable == 0) {
      notebookCollisionBox.setOpacity(1);
    }
  }

  public void deactivateNotebookGlow() {
    if (bookVariable == 0) {
      notebookCollisionBox.setOpacity(0);
    }
  }
}
