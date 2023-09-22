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

  private ChatMessage thinkingMessage =
      new ChatMessage("Wise Mystical Tree", "Allow me to ponder...");
  private ChatMessage activationMessage =
      new ChatMessage("Wise Mystical Tree", "That is good to hear... Allow me to ponder...");
  private ChatCompletionRequest chatCompletionRequest;

  private int firstMission;
  private int secondMission;

  /**
   * Initializes the chat view, loading the riddle.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {

    // Start thinking
    inputText.setDisable(true);
    startThink();
    loading.setVisible(true);
    loadingCircle.setFill(Color.LIGHTGRAY);

    // greets the user at the start.
    Task<Void> greetTask =
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

            gptMessage = runGpt(new ChatMessage("user", GptPromptEngineering.introCall()));

            updateProgress(1, 1);
            return null;
          }
        };

    loading.progressProperty().bind(greetTask.progressProperty());

    greetTask.setOnSucceeded(
        e -> {
          isGenerating = false;
          // End thinking, start talking
          loading.progressProperty().unbind();
          loading.setVisible(false);
          loadingCircle.setFill(Color.valueOf("264f31"));
          inputText.setDisable(false);
          startTalk();
        });

    Thread mainRiddleThread = new Thread(greetTask);
    mainRiddleThread.start();
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
    chatCompletionRequest.addMessage(msg);
    try {
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      chatCompletionRequest.addMessage(result.getChatMessage());
      result.getChatMessage().setRole("Wise Ancient Tree");
      appendChatMessage(result.getChatMessage());
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

    if (!GameState.isGreetingShown) {
      generateRiddle(message);
      GameState.isGreetingShown = true;
      listeningLabel.setVisible(false);
      if (GameState.getDifficulty() != 2) {
        hintButton.setVisible(true);
        hintButton.setDisable(false);
      }
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
                System.out.println("first riddle solved");
                // if (firstMission == 1) {
                //   GameState.missionManager.getMission(MISSION.WINDOW).increaseStage();
                //   GameState.progressBarGroup.updateProgressOne(MISSION.WINDOW);
                //   System.out.println("Window Mission Complete");
                //   System.out.println(
                //       GameState.missionManager.getMission(MISSION.WINDOW).getStage());
                // } else if (firstMission == 2) {
                //   GameState.missionManager.getMission(MISSION.FUEL).increaseStage();
                //   GameState.progressBarGroup.updateProgressOne(MISSION.FUEL);
                //   System.out.println("Fuel Mission Complete");
                //
                // System.out.println(GameState.missionManager.getMission(MISSION.FUEL).getStage());
                // }
              }
            } else if (GameState.firstRiddleSolved && !GameState.secondRiddleSolved) {
              if (lastMsg.getRole().equals("assistant")
                  && lastMsg.getContent().startsWith("Correct")) {
                GameState.secondRiddleSolved = true;
              }
            }

            // if (lastMsg.getRole().equals("assistant")
            //     && lastMsg.getContent().startsWith("Correct")) {
            //   GameState.inventory.add(-1);
            // }
            // if (GameState.inventory.contains(-2)) {
            //   GameState.textToSpeech.speak(lastMsg.getContent());
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

  public ChatMessage invokeRunGpt(ChatMessage msg) throws ApiProxyException {
    return runGpt(msg);
  }

  private void generateRiddle(String message) {

    inputText.setDisable(true);
    startThink();
    loading.setVisible(true);
    loadingCircle.setFill(Color.LIGHTGRAY);

    System.out.println("generate riddle");

    for (int i = 0; i < 2; i++) {
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

            msg.setRole("You");
            appendChatMessage(msg);
            msg.setRole("user");
            appendChatMessage(activationMessage);

            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.5)
                    .setTopP(0.2)
                    .setMaxTokens(150);

            System.out.println("first mission riddle");
            if (firstMission == 1) { // if the first mission is the window
              gptMessage =
                  runGpt(
                      new ChatMessage(
                          "user", GptPromptEngineering.getRiddleWithGivenWordWindow("sand")));
            } else if (firstMission == 2) { // if it is the fuel
              gptMessage =
                  runGpt(
                      new ChatMessage(
                          "user", GptPromptEngineering.getRiddleWithGivenWordWindow("sky")));
            }

            updateProgress(1, 1);
            return null;
          }
        };

    loading.progressProperty().bind(firstRiddleTask.progressProperty());

    firstRiddleTask.setOnSucceeded(
        e2 -> {
          isGenerating = false;
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
