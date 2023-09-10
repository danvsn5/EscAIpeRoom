package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.SceneManager.AppPanel;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

public class LoseController {

  @FXML private Button returnMenuButton;
  @FXML private Label loseTauntMessage;
  private ChatMessage message;
  private ChatCompletionRequest chatCompletionRequest;

  // upon initilaising, a GPT call is made to generate the final taunt that the user sees when if
  // they lose the game and is placed inside the label
  public void initialize() {

    Task<Void> loseTauntGPT =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.7)
                    .setTopP(0.7)
                    .setMaxTokens(100);
            message = runGpt(new ChatMessage("user", GptPromptEngineering.loseCall()));

            Platform.runLater(
                () -> {
                  loseTauntMessage.setText(message.getContent());
                });

            return null;
          }
        };

    Thread loseThread = new Thread(loseTauntGPT);
    loseThread.start();
  }

  // upon losing, gamestate is reset and returns to launch menu when return button is pressed
  public void returnMenu() throws IOException {
    GameState.count = 120;
    GameState.inventory.clear();
    App.setUi(AppPanel.LAUNCH);
  }

  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
    chatCompletionRequest.addMessage(msg);
    try {
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      chatCompletionRequest.addMessage(result.getChatMessage());
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      loseTauntMessage.setText(
          "Sorry, there was a problem generating a response. Please try restarting the"
              + " application.");
      e.printStackTrace();
      return null;
    }
  }
}
