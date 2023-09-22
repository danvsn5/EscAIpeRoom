package nz.ac.auckland.se206.controllers;

import java.io.IOException;
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
  @FXML private Button sendButton;
  @FXML private Button hintButton;
  @FXML private Circle loadingCircle;
  @FXML private Label counter;
  @FXML private Label listeningLabel;
  @FXML private Label hintNumber;
  @FXML private ProgressIndicator loading;
  @FXML private TextArea chatTextArea;
  @FXML private TextField inputText;
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

  private ChatMessage thinkingMessage =
      new ChatMessage("Wise Mystical Tree", "Allow me to ponder...");
  private ChatMessage activationMessage =
      new ChatMessage("Wise Mystical Tree", "That is good to hear... Allow me to ponder...");

  private ChatCompletionRequest chatCompletionRequest;
  public static ChatMessage firstMesage;
  public static int seenFirstMessage = 0;
  public static ChatMessage secondGuideMessage;

  private int firstMission;
  private int secondMission;

  /**
   * Initializes the chat view, loading the riddle.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {

    chatTextArea.setEditable(false); // prevents user from editing the chat text area

    inputText.setDisable(true);

    // Start thinking set up
    startThink();
    loading.setVisible(true);
    loadingCircle.setFill(Color.LIGHTGRAY);

    String mission1;

    // If the first mission is window mission, append relative intro
    if (GameState.missionList.contains(1)) {
      mission1 =
          "Know how to fix the window? I shall give you a riddle and the answer shuold guide you"
              + " to the next step.";
      chatTextArea.appendText(mission1);
    } else if (GameState.missionList.contains(2)) {
      // If the first mission is fuel mission, append relative intro
      mission1 =
          "Know how to charge the fuel? I shall give you a riddle and the answer shuold guide"
              + " you to the next step.";
      chatTextArea.appendText(mission1);
    }

    Task<Void> guideTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            // Record gpt generating
            isGenerating = true;
            // Record gpt setting
            setChatCompletionRequest(
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.7)
                    .setTopP(0.7)
                    .setMaxTokens(150));
            // Create new message
            gptMessage = runGpt(new ChatMessage("user", GptPromptEngineering.introCall()));
            firstMesage = gptMessage;
            // Get the Guide message of the second mission
            if (true) {
              secondGuideMessage =
                  runGpt(
                      new ChatMessage(
                          "user",
                          GptPromptEngineering.getGuideToSecondMission("Fix the Controller")));
              System.out.println("second guide message");
            }
            updateProgress(1, 1);
            return null;
          }
        };

    loading.progressProperty().bind(guideTask.progressProperty());

    guideTask.setOnSucceeded(
        e -> {
          isGenerating = false;
          // End thinking, start talking
          loading.progressProperty().unbind();
          loading.setVisible(false);
          loadingCircle.setFill(Color.valueOf("264f31"));
          inputText.setDisable(false);
          startTalk();
        });

    Thread guideThread = new Thread(guideTask);
    guideThread.start();
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
    }

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
    startListen();

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
    } else if (GameState.isFirstMissionCompleted && !GameState.isSecondGuideShown) {
      generatePuzzle(message);
      GameState.isSecondGuideShown = true;
      listeningLabel.setVisible(false);
      return;
    }

    Task<Void> typeCall =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            isGenerating = true;

            ChatMessage msg = new ChatMessage("user", message);
            msg.setRole("You");
            appendChatMessage(msg);
            System.out.println(msg.getContent());
            msg.setRole("user");
            appendChatMessage(thinkingMessage);
            ChatMessage lastMsg = runGpt(msg);
            lastMsg.setRole("Wise Ancient Tree");
            appendChatMessage(lastMsg);
            lastMsg.setRole("assistant");
            System.out.println("lastMsg");
            // if riddle was solved correctly, then -1 is added to the inventory; -2 is determined
            // from the launch panel and checks whether or not text to speech will be active

            if (!GameState.firstRiddleSolved) {
              System.out.println("first riddle not solved");
              if (lastMsg.getRole().equals("assistant")
                  && lastMsg.getContent().startsWith("Correct")) {
                if (!GameState.firstRiddleSolved && GameState.missionList.contains(2)) {
                  GameState.missionManager.getMission(MISSION.FUEL).increaseStage();
                  GameState.progressBarGroup.updateProgressOne(MISSION.FUEL);
                  System.out.println("Fuel Mission 1 Complete");
                  fuel.setDisable(false);
                  fuel.setVisible(true);
                } else if (!GameState.firstRiddleSolved && GameState.missionList.contains(1)) {
                  GameState.missionManager.getMission(MISSION.WINDOW).increaseStage();
                  GameState.progressBarGroup.updateProgressOne(MISSION.WINDOW);
                  System.out.println("Window riddle solved");
                  sand.setDisable(false);
                  sand.setVisible(true);
                }
                GameState.firstRiddleSolved = true;
                GameState.textToSpeech.speak(lastMsg.getContent());
                System.out.println("first riddle solved");
              }
            } else if (GameState.firstRiddleSolved && !GameState.secondRiddleSolved) {
              if (lastMsg.getRole().equals("assistant")
                  && lastMsg.getContent().startsWith("Correct")) {
                GameState.secondRiddleSolved = true;
              }
            }
            updateProgress(1, 1);
            return null;
          }
        };

    loading.progressProperty().bind(typeCall.progressProperty());

    typeCall.setOnSucceeded(
        e -> {
          isGenerating = false;
          // Start talk
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
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    startListen();
    treeThinking.setVisible(true);
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    System.out.println("key " + event.getCode() + " released");
    if (inputText.getText().trim().isEmpty()) {
      startTalk();
      listeningLabel.setVisible(false);
    }
  }

  public void activateProgressGlow() {
    progressButton.setEffect(GameState.glowBright);
  }

  public void deactivateProgressGlow() {
    progressButton.setEffect(GameState.glowDim);
  }

  private void generateFirstRiddle(String message) {
    // This method generates the first riddle
    // Disable the text box
    inputText.setDisable(true);
    // Start thinking
    startThink();
    loading.setVisible(true);
    loadingCircle.setFill(Color.LIGHTGRAY);
    // Get the type of missions and record them
    for (int i = 0; i < 2; i++) {
      System.out.println("----");
      if (GameState.missionList.get(i) == 1 || GameState.missionList.get(i) == 2) {
        firstMission = GameState.missionList.get(i);
      } else {
        secondMission = GameState.missionList.get(i);
      }
    }

    Task<Void> firstRiddleTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            // Record that gpt is generating
            isGenerating = true;
            // Create new message
            ChatMessage msg = new ChatMessage("user", message);
            msg.setRole("You");
            appendChatMessage(msg);
            msg.setRole("user");
            appendChatMessage(activationMessage);
            // Record gpt setting
            setChatCompletionRequest(
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.5)
                    .setTopP(0.2)
                    .setMaxTokens(150));

            // Generate first mission
            if (firstMission == 1) { // if the first mission is the window
              gptMessage =
                  runGpt(
                      new ChatMessage(
                          "user", GptPromptEngineering.getRiddleWithGivenWordWindow("sand")));
              gptMessage.setRole("Wise Ancient Tree");
              appendChatMessage(gptMessage);
              gptMessage.setRole("assistant");
            } else if (firstMission == 2) { // if it is the fuel
              gptMessage =
                  runGpt(
                      new ChatMessage(
                          "user", GptPromptEngineering.getRiddleWithGivenWordWindow("sky")));
              gptMessage.setRole("Wise Ancient Tree");
              appendChatMessage(gptMessage);
              gptMessage.setRole("assistant");
            }

            updateProgress(1, 1);
            return null;
          }
        };

    loading.progressProperty().bind(firstRiddleTask.progressProperty());

    firstRiddleTask.setOnSucceeded(
        e2 -> {
          // Record that generating stops
          isGenerating = false;
          loading.progressProperty().unbind();
          startTalk();
          loading.setVisible(false);
          loadingCircle.setFill(Color.valueOf("264f31"));
          inputText.setDisable(false);
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
    // This method collects fuel
    GameState.inventory.add(8); // fuel collected
    // Increase fuel mission stage and update progressbar
    GameState.missionManager.getMission(MISSION.FUEL).increaseStage();
    GameState.progressBarGroup.updateProgressOne(MISSION.FUEL);
    // Hide fuel
    fuel.setVisible(false);
    fuel.setDisable(true);
    // Show message
    SceneManager.showDialog("Info", "Fuel Collected", "A heavy fuel tank");
  }

  public void activateSandGlow() {
    sand.setEffect(GameState.glowBright);
  }

  public void deactivateSandGlow() {
    sand.setEffect(GameState.glowDim);
  }

  public void collectSand() {
    // This method collects sand
    // Increase the window mission by one stage and update progressbar
    GameState.missionManager.getMission(MISSION.WINDOW).increaseStage();
    GameState.progressBarGroup.updateProgressOne(MISSION.WINDOW);
    // Hide the sand
    sand.setVisible(false);
    sand.setDisable(true);
    // Add sand to inventory
    GameState.inventory.add(2);
    // Show collect message
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

    Task<Void> secondPuzzleTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            ChatMessage msg = new ChatMessage("user", message);
            appendChatMessage(msg);

            setChatCompletionRequest(
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.5)
                    .setTopP(0.2)
                    .setMaxTokens(100));

            System.out.println("first mission riddle");
            if (firstMission == 3) { // if the first mission is the controller
              gptMessage =
                  runGpt(new ChatMessage("user", GptPromptEngineering.getControllerPuzzle()));
              gptMessage.setRole("Wise Ancient Tree");
              appendChatMessage(gptMessage);
              gptMessage.setRole("assistant");
            } else if (firstMission == 4) { // if it is the thruster
              if (GameState.randomColorNumber == 1) { // red
                gptMessage =
                    runGpt(new ChatMessage("user", GptPromptEngineering.getThrusterPuzzle("red")));
                gptMessage.setRole("Wise Ancient Tree");
                appendChatMessage(gptMessage);
                gptMessage.setRole("assistant");
              } else if (GameState.randomColorNumber == 2) { // blue
                gptMessage =
                    runGpt(new ChatMessage("user", GptPromptEngineering.getThrusterPuzzle("blue")));
                gptMessage.setRole("Wise Ancient Tree");
                appendChatMessage(gptMessage);
                gptMessage.setRole("assistant");
              } else if (GameState.randomColorNumber == 3) { // green
                gptMessage =
                    runGpt(
                        new ChatMessage("user", GptPromptEngineering.getThrusterPuzzle("green")));
                gptMessage.setRole("Wise Ancient Tree");
                appendChatMessage(gptMessage);
                gptMessage.setRole("assistant");
              } else if (GameState.randomColorNumber == 4) { // purple
                gptMessage =
                    runGpt(
                        new ChatMessage("user", GptPromptEngineering.getThrusterPuzzle("purple")));
                gptMessage.setRole("Wise Ancient Tree");
                appendChatMessage(gptMessage);
                gptMessage.setRole("assistant");
              }
            }

            updateProgress(1, 1);
            return null;
          }
        };

    loading.progressProperty().bind(secondPuzzleTask.progressProperty());

    secondPuzzleTask.setOnSucceeded(
        e2 -> {
          loading.progressProperty().unbind();
          loading.setVisible(false);
          loadingCircle.setFill(Color.valueOf("264f31"));
          inputText.setDisable(false);
          startTalk();
        });

    Thread secondPuzzleThread = new Thread(secondPuzzleTask);
    secondPuzzleThread.start();
  }

  @FXML
  private void getHint(ActionEvent event) throws ApiProxyException, IOException {
    // If the hint is used up, return
    if (GameState.hintUsedUp()) {
      SceneManager.showDialog("Info", "Hint number used up", "No more hint allowed");
      return;
    }
    // If gpt is generating answer, return
    if (isGenerating) {
      SceneManager.showDialog("Info", "Tree is thinking, don't interrupt him", "Quiet!");
      return;
    }
    // If first mission is not complete, choose between window and fuel
    if (!GameState.isFirstMissionCompleted) {
      if (GameState.missionList.contains(1)) {
        // If window mission is activate, ask window hint
        askByStage(MISSION.WINDOW);
      } else {
        // If fuel mission is activate, ask fuel hint
        askByStage(MISSION.FUEL);
      }
    } else {
      // If first mission is completed, choose between controller and thruster
      if (GameState.missionList.contains(3)) {
        // Choose controller
        askByStage(MISSION.CONTROLLER);
      } else {
        // Choose thruster
        askByStage(MISSION.THRUSTER);
      }
    }
    // Record that a hint is used
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
            // Record that gpt is generating answer
            isGenerating = true;
            // Record the setting of gpt
            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.7)
                    .setTopP(0.7)
                    .setMaxTokens(150);
            // Runs gpt and get message using hint prompt
            gptMessage = runGpt(new ChatMessage("user", GptPromptEngineering.getHint(missionType)));
            // Set the role of gpt's answer to tree
            gptMessage.setRole("Wise Ancient Tree");
            // Show the message on the textbox
            appendChatMessage(gptMessage);
            gptMessage.setRole("assistant");
            // Update progress circle
            updateProgress(1, 1);
            return null;
          }
        };

    hintTask.setOnSucceeded(
        e -> {
          isGenerating = false;
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
}
