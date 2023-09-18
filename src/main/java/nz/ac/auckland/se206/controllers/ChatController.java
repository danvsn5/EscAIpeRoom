package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import nz.ac.SceneManager;
import nz.ac.SceneManager.AppPanel;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

/** Controller class for the chat view. */
public class ChatController {
  public static ChatMessage gptMessage;

  @FXML private TextArea chatTextArea;
  @FXML private TextField inputText;
  @FXML private Button sendButton;
  @FXML private Label counter;
  @FXML private ImageView progressButton;

  private ChatMessage thinkingMessage =
      new ChatMessage("Wise Mystical Tree", "Allow me to ponder...");
  private ChatCompletionRequest chatCompletionRequest;

  /**
   * Initializes the chat view, loading the riddle.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> dispCount()));
    timeline.setCycleCount(123);
    timeline.play();

    Task<Void> riddleCall =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.7)
                    .setTopP(0.7)
                    .setMaxTokens(100);

            gptMessage =
                runGpt(
                    new ChatMessage("user", GptPromptEngineering.getRiddleWithGivenWord("sand")));

            return null;
          }
        };

    Thread mainRiddleThread = new Thread(riddleCall);
    mainRiddleThread.start();
  }

  public void dispCount() {
    counter.setText(String.valueOf(GameState.count));
  }

  public void goProgress() {
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
      result.getChatMessage().setRole("Wise Mystical Tree");
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
    String message = inputText.getText();
    if (message.trim().isEmpty()) {
      return;
    }
    inputText.clear();

    Task<Void> typeCall =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            ChatMessage msg = new ChatMessage("user", message);
            msg.setRole("You");
            appendChatMessage(msg);
            msg.setRole("user");
            appendChatMessage(thinkingMessage);
            ChatMessage lastMsg = runGpt(msg);

            // if riddle was solved correctly, then -1 is added to the inventory; -2 is determined
            // from the launch panel and checks whether or not text to speech will be active

            if (lastMsg.getRole().equals("assistant")
                && lastMsg.getContent().startsWith("Correct")) {
              GameState.inventory.add(-1);
            }
            if (GameState.inventory.contains(-2)) {
              GameState.textToSpeech.speak(lastMsg.getContent());
            }
            return null;
          }
        };

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
    SceneManager.setPrevious(AppPanel.OUTSIDE);
    App.setUi(AppPanel.OUTSIDE);
  }

  public void activateProgressGlow() {
    progressButton.setEffect(GameState.glowBright);
  }

  public void deactivateProgressGlow() {
    progressButton.setEffect(GameState.glowDim);
  }
}
