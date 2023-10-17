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
import nz.ac.auckland.se206.MissionManager.MissionType;
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
  public static ChatCompletionRequest chatCompletionRequest;
  public static ChatCompletionRequest hintChatCompletionRequest;
  public static ChatMessage firstMesage;
  public static int seenFirstMessage = 0;
  public static ChatMessage secondGuideMessage;

  @FXML private Button sendButton;
  @FXML private Button hintButton;
  @FXML private Button closeBookButton;

  @FXML private Label chatLabel;
  @FXML private Label counter;
  @FXML private Label listeningLabel;
  @FXML private Label hintNumber;
  @FXML private Label collectedLabel;
  @FXML private Label collectedTitle;

  @FXML private ProgressIndicator loading;

  @FXML private TextArea chatTextArea;
  @FXML private TextField inputText;

  @FXML private ImageView progressButton;
  @FXML private ImageView treeListening;
  @FXML private ImageView treeTalking;
  @FXML private ImageView treeThinking;
  @FXML private ImageView rootInitial;
  @FXML private ImageView rootOne;
  @FXML private ImageView rootTwo;
  @FXML private ImageView rootThree;
  @FXML private ImageView smallBubble;
  @FXML private ImageView largeBubble;
  @FXML private ImageView medBubble;
  @FXML private ImageView notebook;
  @FXML private ImageView zoomBook;
  @FXML private ImageView sandInfo;
  @FXML private ImageView fuelInfo;

  @FXML private Circle loadingCircle;
  @FXML private Polygon notebookCollisionBox;
  @FXML private Rectangle hintRectangle;

  private int bubbleVariable = 0;
  private int bookVariable = 0;
  private boolean isGenerating = false;
  private int firstMission;
  private Timeline bubbleTimeline =
      new Timeline(new KeyFrame(javafx.util.Duration.millis(333), e -> thinkBubble()));

  /**
   * Initializes settings for images in chat room, greets the gpt.
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

    startThink();
    initializeCompletionRequest();

    // Get the intro message for mission 1
    String mission1;
    if (GameState.missionListA.contains(1)) { // if the first mission is the window
      mission1 =
          "So you have found me and wish to escape MY planet? I wish to make your attempts as"
              + " challenging as possible. To repair your window, I shall offer you a riddle. \n\n"
              + " Are you ready? \n\n";
      chatTextArea.appendText("Wise Ancient Tree: " + mission1);
      chatLabel.setText(mission1);
      System.out.println("chatLineCode");

    } else if (GameState.missionListA.contains(2)) { // if it is the fuel
      mission1 =
          "So you have found me and wish to escape MY planet? I wish to make your attempts as"
              + " challenging as possible. To refuel you ship, I shall offer you a riddle. \n\n"
              + " Are you ready? \n\n";
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

  public void goProgress() {
    SceneManager.setPrevious(AppPanel.CHAT);
    App.setUi(AppPanel.PROGRESS);
  }

  /**
   * Appends a chat message to the chat text area.
   *
   * @param msg the chat message to append
   */
  public void appendChatMessage(ChatMessage msg) {
    //   chatTextArea.appendText(msg.getRole() + ": " + msg.getContent() + "\n\n");
    chatLabel.setText(msg.getContent());
  }

  public void appendToNotebook(ChatMessage msg) {
    chatTextArea.appendText(msg.getRole() + ": " + msg.getContent() + "\n\n");
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

  /** Activate hint button according to difficulty. */
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

  /**
   * Process the response from gpt, detect if it starts with Correct and execute relavent code.
   *
   * @param response the response from gpt.
   */
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
        GameState.missionManager.getMission(MissionType.WINDOW).increaseStage();
        GameState.progressBarGroup.updateProgressOne(MissionType.WINDOW);
        System.out.println("Window riddle solved");
        showSand();
      } else if (GameState.missionList.contains(2)) {
        // If the player guesses correctly and its fuel mission, show the fuel and
        // increase stage
        GameState.missionManager.getMission(MissionType.FUEL).increaseStage();
        GameState.progressBarGroup.updateProgressOne(MissionType.FUEL);
        System.out.println("Fuel Mission 1 Complete");
        showFuel();
      }
      // Speak the correct response from gpt and mark first riddle as solved
      GameState.speak(response);
      GameState.firstRiddleSolved = true;
    } else if (GameState.missionList.contains(4)) {
      // If it is currently second mission and it's thruster mission, increase stage
      GameState.missionManager.getMission(MissionType.THRUSTER).increaseStage();
      GameState.progressBarGroup.updateProgressTwo(MissionType.THRUSTER);
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
   * @param event the key event.
   * @throws IOException if there is an I/O error.
   * @throws ApiProxyException if there is an error communicating with the API proxy.
   */
  @FXML
  public void onKeyPressed(KeyEvent event) throws ApiProxyException, IOException {
    inputText.setStyle(
        "-fx-background-color: transparent;"); // set the input text field to be transparent

    startListen();
    treeThinking.setVisible(true);
    if (event.getCode().toString().equals("ENTER")) { // if the key pressed is enter
      onSendMessage(new ActionEvent()); // send the message
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
    if (inputText.getText().trim().isEmpty()) { // if the input text field is empty
      startTalk();
      listeningLabel.setVisible(false); // hide the listening label
    }
    inputText.setStyle(
        "-fx-background-color: transparent;"); // set the input text field to be transparent
  }

  public void activateProgressGlow() {
    progressButton.setEffect(GameState.glowBright);
  }

  public void deactivateProgressGlow() {
    progressButton.setEffect(GameState.glowDim);
  }

  /** Generate the first riddle from gpt, append the response to chat field and notebook. */
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
  }

  /** Thie method handles feature of collecting fuel and bucket. */
  public void collect() {
    // If the first mission is fuel mission
    if (GameState.missionList.contains(2)) {
      GameState.inventory.add(8); // fuel collected
      GameState.missionManager.getMission(MissionType.FUEL).increaseStage();
      GameState.progressBarGroup.updateProgressOne(MissionType.FUEL);
      System.out.println("Fuel Mission 2 Complete");
    } else if (GameState.missionList.contains(1)) {
      // If the first mission is bucket mission, collect bucket
      GameState.isBucketCollected = true;
    }
    exitInfo();
  }

  /** Show the tree listening image and hide other tree iomages. */
  private void startListen() {
    treeListening.setVisible(true);
    treeTalking.setVisible(false);
    treeThinking.setVisible(false);
  }

  /** Show the tree talking image and hide other tree iomages. */
  private void startTalk() {
    treeListening.setVisible(false);
    treeTalking.setVisible(true);
    treeThinking.setVisible(false);
  }

  /** Show the tree thinking image and hide other tree iomages. */
  private void startThink() {
    treeListening.setVisible(false);
    treeTalking.setVisible(false);
    treeThinking.setVisible(true);
  }

  /**
   * Get hint response from gpt based on input message and completion request.
   *
   * @param event the action event triggered by the hint button.
   * @throws ApiProxyException if there is an error communicating with the API proxy.
   * @throws IOException if there is an I/O error.
   */
  @FXML
  private void onGetHint(ActionEvent event) throws ApiProxyException, IOException {
    // If the gpt is generating response or hint number used up, return
    if (GameState.hintUsedUp() || isGenerating) {
      return;
    }
    // Ask hint based on different mission and stage of the game
    if (!GameState.isFirstMissionCompleted) {
      if (GameState.missionList.contains(1)) {
        System.out.println("Window hint");
        askHintByStage(MissionType.WINDOW);
      } else {
        System.out.println("Fuel hint");
        askHintByStage(MissionType.FUEL);
      }
    } else {
      if (GameState.missionList.contains(3)) {
        System.out.println("Controller hint");
        askHintByStage(MissionType.CONTROLLER);
      } else {
        System.out.println("Thruster hint");
        askHintByStage(MissionType.THRUSTER);
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

  /**
   * Ask hint from gpt based on mission and stage of the game.
   *
   * @param missionType the mission type.
   */
  private void askHintByStage(MissionType missionType) {
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

  /**
   * Changes the size of the chat bubble based on the current state of the bubbleVariable. There are
   * 7 different states for the bubbleVariable: 0 = no bubble 1 = small bubble 2 = medium bubble 3 =
   * large bubble 4 = medium bubble 5 = small bubble 6 = no bubble.
   */
  public void thinkBubble() {
    switch (bubbleVariable) {
      case 0:
        // When the bubble is in state 0, activate the small bubble and set state to 1
        smallBubble.setVisible(true);
        bubbleVariable = 1;
        break;
      case 1:
        // When the bubble is in state 1, activate the medium bubble and set state to 2
        medBubble.setVisible(true);
        bubbleVariable = 2;
        break;
      case 2:
        // When the bubble is in state 2, activate the large bubble and set state to 3
        largeBubble.setVisible(true);
        bubbleVariable = 3;
        break;
      case 3:
        // When the bubble is in state 3, deactivate the large bubble and set state to 4
        largeBubble.setVisible(false);
        bubbleVariable = 4;
        break;
      case 4:
        // When the bubble is in state 4, deactivate the medium bubble and set state to 5
        medBubble.setVisible(false);
        bubbleVariable = 5;
        break;
      case 5:
        // When the bubble is in state 5, deactivate the small bubble and set state to 6
        smallBubble.setVisible(false);
        bubbleVariable = 0;
        break;
    }
  }

  /** Open the notebook and its text area. */
  public void openBook() {
    zoomBook.setVisible(true);
    bookVariable = 1;
    notebookCollisionBox.setOpacity(0);
    chatTextArea.setVisible(true);
    closeBookButton.setVisible(true);
  }

  /** Close the notebook and its text area. */
  public void closeBook() {
    zoomBook.setVisible(false);
    chatTextArea.setVisible(false);
    closeBookButton.setVisible(false);
    bookVariable = 0;
  }

  /** Activates the glow effect for the notebook if the bookVariable is 0. */
  public void activateNotebookGlow() {
    if (bookVariable == 0) {
      notebookCollisionBox.setOpacity(1);
    }
  }

  /** Deactivates the glow effect of the notebook if the bookVariable is equal to 0. */
  public void deactivateNotebookGlow() {
    if (bookVariable == 0) {
      notebookCollisionBox.setOpacity(0);
    }
  }

  /** This method closes all info panel in this page. */
  public void exitInfo() {
    collectedLabel.setVisible(false);
    sandInfo.setVisible(false);
    fuelInfo.setVisible(false);
    collectedTitle.setVisible(false);
  }

  /** This method shows the sand info panel. */
  public void showSand() {
    // Set the title and context of the info panel
    collectedTitle.setText("Bucket");
    collectedLabel.setText("An empty bucket.\nIt may be used to collect sand");
    // Show the sand info panel
    sandInfo.setVisible(true);
    collectedTitle.setVisible(true);
    collectedLabel.setVisible(true);
  }

  /** This method shows the fuel info panel. */
  public void showFuel() {
    // Set the title and context of the info panel
    collectedTitle.setText("Fuel");
    collectedLabel.setText("A heavy fuel tank");
    // Show the fuel info panel
    fuelInfo.setVisible(true);
    collectedTitle.setVisible(true);
    collectedLabel.setVisible(true);
  }

  /** Initialize the two completion requests. */
  private void initializeCompletionRequest() {
    chatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(0.7).setTopP(0.5).setMaxTokens(100);
    hintChatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(0.3).setTopP(0.3).setMaxTokens(100);
  }

  /**
   * This method generates response from gpt based on the message and current completion request.
   *
   * @param message the message sent to gpt.
   * @param currentCompletionRequest the current completion request.
   * @return the response from gpt.
   * @throws ApiProxyException if there is an error communicating with the API proxy.
   */
  public static ChatMessage getResponse(
      String message, ChatCompletionRequest currentCompletionRequest) throws ApiProxyException {
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
