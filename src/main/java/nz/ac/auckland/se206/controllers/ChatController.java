package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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

  @FXML private ImageView sandInfo;
  @FXML private ImageView fuelInfo;
  @FXML private Label collectedLabel;
  @FXML private Label collectedTitle;

  private int bubbleVariable = 0;
  private int bookVariable = 0;
  private ChatMessage thinkingMessage =
      new ChatMessage("Wise Mystical Tree", "Allow me to ponder...");
  private ChatMessage activationMessage =
      new ChatMessage("Wise Mystical Tree", "That is good to hear... Allow me to ponder...");

  public static ChatCompletionRequest chatCompletionRequest;
  public static ChatCompletionRequest hintChatCompletionRequest;
  public static ChatMessage firstMesage;
  public static int seenFirstMessage = 0;
  public static ChatMessage secondGuideMessage;
  Timeline bubbleTimeline =
      new Timeline(new KeyFrame(javafx.util.Duration.millis(333), e -> thinkBubble()));
  private int firstMission;
  private int secondMission;

  /**
   * Initializes settings for images in chat room, greets the gpt
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
    // Set up thinking bubble, notebook, chat area, hint button, input text field
    bubbleTimeline.setCycleCount(Timeline.INDEFINITE);
    notebookCollisionBox.setCursor(Cursor.HAND);
    chatTextArea.setEditable(false); // prevents user from editing the chat text area
    hintButton.setDisable(true);
    inputText.setDisable(true);
    inputText.setStyle("-fx-background-color: transparent;");

    // Disable the old loading effect
    loading.setVisible(false);
    loadingCircle.setVisible(false);

    // Start thinking
    startThink();
    initializeCompletionRequest();

    // Get the intro message for mission 1
    String mission1;
    if (GameState.missionListA.contains(1)) {
      mission1 =
          "Know how to fix the window? I shall give you a riddle and the answer shuold guide you"
              + " to the next step. Are you ready? \n\n";
      chatTextArea.appendText("Wise Ancient Tree: " + mission1);
      chatLabel.setText(mission1);
      System.out.println("chatLineCode");

    } else if (GameState.missionListA.contains(2)) {
      mission1 =
          "Know how to charge the fuel? I shall give you a riddle and the answer shuold guide"
              + " you to the next step. Are you ready? \n\n";
      chatTextArea.appendText("Wise Ancient Tree: " + mission1);
      System.out.println("chatLineCode");
      chatLabel.setText(mission1);
    }

    // This task greets gpt, the message generated is not shown
    Task<Void> introCall =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            // Start generating
            isGenerating = true;
            // Greets gpt, this message is not shown or use TTS
            gptMessage = getResponse(GptPromptEngineering.introCall(), chatCompletionRequest);
            return null;
          }
        };

    // When the intro thread is finished
    introCall.setOnSucceeded(
        e -> {
          // Stop generating
          isGenerating = false;
          // Stop the thinking animation
          smallBubble.setVisible(false);
          medBubble.setVisible(false);
          largeBubble.setVisible(false);
          bubbleTimeline.pause();
          bubbleVariable = 0;
          startTalk();
          // Activate hint and input text field
          inputText.setDisable(false);
          hintButton.setDisable(false);
        });

    // Start the thread
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

  private void appendToNotebook(ChatMessage msg) {
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
    // If gpt is generating or the input is empty, return
    if (isGenerating) {
      return;
    } else if (inputText.getText().trim().isEmpty()) {
      return;
    }

    // Start thinking animation, disable input text field and hint button
    startThink();
    bubbleTimeline.play();
    inputText.setDisable(true);
    hintButton.setDisable(true);

    // Get the input
    String message = inputText.getText();
    inputText.clear();

    // Append the input to notebook
    appendToNotebook(new ChatMessage("Me", message));

    // If this is the first time answering, generate first riddle
    if (!GameState.isGreetingShown && !GameState.isFirstMissionCompleted) {
      getMissionId();
      activateHintButton();
      generateFirstRiddle();
      GameState.isGreetingShown = true;
      listeningLabel.setVisible(false);
      return;
    }

    // Send message to gpt to ask for response
    Task<Void> typeCall =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            // Start generation
            isGenerating = true;
            // Get the response
            ChatMessage response = getResponse(message, chatCompletionRequest);
            // Append the response to text field and notebook
            response.setRole("Wise Ancient Tree");
            Platform.runLater(() -> appendChatMessage(response));
            Platform.runLater(() -> appendToNotebook(response));
            Platform.runLater(() -> response.setRole("assistant"));

            // Process the response from gpt, detect if it says correct
            Platform.runLater(() -> processResponse(response.getContent()));

            updateProgress(1, 1);
            return null;
          }
        };

    typeCall.setOnSucceeded(
        e -> {
          // Stop generating
          isGenerating = false;
          // Stop thinking animation and start talking
          smallBubble.setVisible(false);
          medBubble.setVisible(false);
          largeBubble.setVisible(false);
          bubbleTimeline.pause();
          bubbleVariable = 0;
          startTalk();
          // Activate input text field and hint button
          inputText.setDisable(false);
          hintButton.setDisable(false);
        });

    // Start the thread
    Thread typeInThread = new Thread(typeCall);
    typeInThread.start();
  }

  /* Activate hint button according to difficulty */
  private void activateHintButton() {
    // Activate hint button if its not hard
    if (GameState.getDifficulty() != 2) {
      hintButton.setVisible(true);
      // Activate hint counter if its medium
      if (GameState.getDifficulty() == 1) {
        hintNumber.setVisible(true);
        hintNumber.setDisable(false);
        hintRectangle.setVisible(true);
        hintRectangle.setDisable(false);
      }
    }
  }

  /* Process with the gpt's response, if it starts with correct, update the game progress and show relavent message/image */
  private void processResponse(String response) {
    // If the gpt is not answering with correct, return
    if (!response.startsWith("Correct")) {
      return;
    }

    // If the first riddle is not solved, mark it as solved
    if (!GameState.firstRiddleSolved) {
      if (GameState.missionList.contains(1)) {
        // If the player guesses correctly and its window mission, show the sand and
        // increase stage
        GameState.missionManager.getMission(MISSION.WINDOW).increaseStage();
        GameState.progressBarGroup.updateProgressOne(MISSION.WINDOW);
        System.out.println("Window riddle solved");
        showSand();
      } else if (GameState.missionList.contains(2)) {
        // If the player guesses correctly and its fuel mission, show the fuel and
        // increase stage
        GameState.missionManager.getMission(MISSION.FUEL).increaseStage();
        GameState.progressBarGroup.updateProgressOne(MISSION.FUEL);
        System.out.println("Fuel Mission 1 Complete");
        showFuel();
      }
      // Speak the correct response from gpt and mark first riddle as solved
      GameState.speak(response);
      GameState.firstRiddleSolved = true;
    } else if (GameState.missionList.contains(4)) {
      // If it is currently second mission and it's thruster mission, increase stage
      GameState.missionManager.getMission(MISSION.THRUSTER).increaseStage();
      GameState.progressBarGroup.updateProgressTwo(MISSION.THRUSTER);
      System.out.println("Thruster riddle solved");
    }
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

  /** Generate the first riddle from gpt, append the response to chat field and notebook */
  private void generateFirstRiddle() {
    Task<Void> firstRiddleTask =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            // Start generating
            isGenerating = true;
            hintButton.setDisable(true);
            // Get riddle from gpt
            if (firstMission == 1) { // if the first mission is the window
              gptMessage =
                  getResponse(
                      GptPromptEngineering.getRiddleWithGivenWord("sand"), chatCompletionRequest);
              gptMessage.setRole("Wise Ancient Tree");
            } else { // if it is the fuel
              gptMessage =
                  getResponse(
                      GptPromptEngineering.getRiddleWithGivenWord("sky"), chatCompletionRequest);
              gptMessage.setRole("Wise Ancient Tree");
            }
            // Append the response to text field and notebook
            Platform.runLater(() -> appendChatMessage(gptMessage));
            Platform.runLater(() -> appendToNotebook(gptMessage));
            Platform.runLater(() -> gptMessage.setRole("assistant"));

            updateProgress(1, 1);
            return null;
          }
        };

    firstRiddleTask.setOnSucceeded(
        e2 -> {
          // Stop generating
          isGenerating = false;
          // Stop thinking animation
          smallBubble.setVisible(false);
          medBubble.setVisible(false);
          largeBubble.setVisible(false);
          bubbleTimeline.pause();
          bubbleVariable = 0;
          startTalk();
          // Activate input text field and hint button
          inputText.setDisable(false);
          hintButton.setDisable(false);
        });

    // Start the thread
    Thread firstRiddleThread = new Thread(firstRiddleTask);
    firstRiddleThread.start();
  }

  private void getMissionId() {
    firstMission = GameState.missionList.get(0);
    secondMission = GameState.missionList.get(1);
  }

  public void collect() {
    if (GameState.missionList.contains(2)) {
      GameState.inventory.add(8); // fuel collected
      GameState.missionManager.getMission(MISSION.FUEL).increaseStage();
      GameState.progressBarGroup.updateProgressOne(MISSION.FUEL);
      System.out.println("Fuel Mission 2 Complete");
    } else if (GameState.missionList.contains(1)) {
      GameState.isBucketCollected = true;
    }
    exitInfo();
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

    // loading.setVisible(true);
    // loadingCircle.setFill(Color.LIGHTGRAY);

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

    //   // loading.progressProperty().bind(secondPuzzleTask.progressProperty());

    //   secondPuzzleTask.setOnSucceeded(
    //       e2 -> {
    //         // loading.progressProperty().unbind();
    //         // loading.setVisible(false);
    //         // loadingCircle.setFill(Color.valueOf("264f31"));
    //         inputText.setDisable(false);
    //         startTalk();
    //       });

    //   Thread secondPuzzleThread = new Thread(secondPuzzleTask);
    //   secondPuzzleThread.start();
  }

  @FXML
  private void getHint(ActionEvent event) throws ApiProxyException, IOException {
    // If the gpt is generating response or hint number used up, return
    if (GameState.hintUsedUp() || isGenerating) {
      return;
    }
    // Ask hint based on different mission and stage of the game
    if (!GameState.isFirstMissionCompleted) {
      if (GameState.missionList.contains(1)) {
        System.out.println("Window hint");
        askHintByStage(MISSION.WINDOW);
      } else {
        System.out.println("Fuel hint");
        askHintByStage(MISSION.FUEL);
      }
    } else {
      if (GameState.missionList.contains(3)) {
        System.out.println("Controller hint");
        askHintByStage(MISSION.CONTROLLER);
      } else {
        System.out.println("Thruster hint");
        askHintByStage(MISSION.THRUSTER);
      }
    }
    // Use a hint, if there is no hint left, hide the hint button
    GameState.useHint();
    if (GameState.hintUsedUp()) {
      hintNumber.setVisible(false);
      hintRectangle.setVisible(false);
      hintButton.setVisible(false);
    }
  }

  /** Ask hint from gpt based on mission and stage of the game */
  private void askHintByStage(MISSION missionType) {
    // Start thinking animation
    bubbleTimeline.play();
    startThink();
    // Disable input text field and hint button
    inputText.setDisable(true);
    hintButton.setDisable(true);

    Task<Void> hintTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            // Start generating
            isGenerating = true;
            System.out.println("start generating");
            // Get the hint from gpt
            gptMessage =
                getResponse(GptPromptEngineering.getHint(missionType), hintChatCompletionRequest);
            System.out.println("end generating");
            // Append the hint to text field and notebook
            gptMessage.setRole("Wise Ancient Tree");
            Platform.runLater(() -> appendChatMessage(gptMessage));
            Platform.runLater(() -> appendToNotebook(gptMessage));
            Platform.runLater(() -> gptMessage.setRole("assistant"));

            updateProgress(1, 1);
            return null;
          }
        };

    hintTask.setOnSucceeded(
        e -> {
          // Stop generating
          isGenerating = false;
          // Stop thinking animation and start talking
          smallBubble.setVisible(false);
          medBubble.setVisible(false);
          largeBubble.setVisible(false);
          bubbleTimeline.pause();
          bubbleVariable = 0;
          startTalk();
          // Activate input text field and hint button
          inputText.setDisable(false);
          hintButton.setDisable(false);
        });

    // Start the thread
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

  /* Open the notebook */
  public void openBook() {
    zoomBook.setVisible(true);
    bookVariable = 1;
    notebookCollisionBox.setOpacity(0);
    chatTextArea.setVisible(true);
    closeBookButton.setVisible(true);
  }

  /* Close the notebook */
  public void closeBook() {
    zoomBook.setVisible(false);
    chatTextArea.setVisible(false);
    closeBookButton.setVisible(false);
    bookVariable = 0;
  }

  /* Activate the yellow collision box of notebook */
  public void activateNotebookGlow() {
    if (bookVariable == 0) {
      notebookCollisionBox.setOpacity(1);
    }
  }

  /* Deactivate the yellow collision box of notebook */
  public void deactivateNotebookGlow() {
    if (bookVariable == 0) {
      notebookCollisionBox.setOpacity(0);
    }
  }

  /* This method closes all info panel in this page */
  public void exitInfo() {
    collectedLabel.setVisible(false);
    sandInfo.setVisible(false);
    fuelInfo.setVisible(false);
    collectedTitle.setVisible(false);
  }

  /* This method shows the sand info panel */
  public void showSand() {
    // Set the title and context of the info panel
    collectedTitle.setText("Bucket");
    collectedLabel.setText("An empty bucket.\nCan be used to collect the sand.");
    // Show the sand info panel
    sandInfo.setVisible(true);
    collectedTitle.setVisible(true);
    collectedLabel.setVisible(true);
  }

  /* This method shows the fuel info panel */
  public void showFuel() {
    // Set the title and context of the info panel
    collectedTitle.setText("Fuel");
    collectedLabel.setText("A heavy fuel tank");
    // Show the fuel info panel
    fuelInfo.setVisible(true);
    collectedTitle.setVisible(true);
    collectedLabel.setVisible(true);
  }

  private void initializeCompletionRequest() {
    chatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(0.7).setTopP(0.5).setMaxTokens(100);
    hintChatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(0.3).setTopP(0.3).setMaxTokens(100);
  }

  private ChatMessage getResponse(String message, ChatCompletionRequest currentCompletionRequest)
      throws ApiProxyException {
    // Turn string message into chatmessage form
    ChatMessage msg = new ChatMessage("user", message);
    currentCompletionRequest.addMessage(msg);
    // Generate result from gpt
    ChatCompletionResult chatCompletionResult = currentCompletionRequest.execute();
    Choice result = chatCompletionResult.getChoices().iterator().next();
    // Set the result's role to assistant
    currentCompletionRequest.addMessage(result.getChatMessage());
    result.getChatMessage().setRole("assistant");
    return result.getChatMessage();
  }
}
