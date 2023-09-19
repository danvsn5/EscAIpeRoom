package nz.ac.auckland.se206.controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import nz.ac.SceneManager.AppPanel;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

public class WorkController {
  @FXML private Rectangle workReturnDoor;
  @FXML private Rectangle furnace;
  @FXML private Rectangle bench;
  @FXML private Label counter;
  @FXML private Label workTauntMessage;
  @FXML private Rectangle cross;
  @FXML private Rectangle workBackground;
  @FXML private Line crossOne;
  @FXML private Line crossTwo;
  @FXML private Label craftAll;
  @FXML private Label craftWindow;
  @FXML private Label craftPanel;
  @FXML private Label craftNone;
  @FXML private Label questionLabel;
  @FXML private Label centralLabel;
  @FXML private Label sandLabel;
  @FXML private Label voiceLabel;
  @FXML private ImageView progressButton;

  private ChatMessage message;
  private ChatCompletionRequest chatCompletionRequest;

  public void initialize() {
    // calls GPT to make a taunt and sets the label once the taunt has been generated
    Task<Void> workTauntGPT =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            chatCompletionRequest =
                new ChatCompletionRequest()
                    .setN(1)
                    .setTemperature(0.4)
                    .setTopP(0.4)
                    .setMaxTokens(100);
            message = runGpt(new ChatMessage("user", GptPromptEngineering.workTaunt()));

            Platform.runLater(
                () -> {
                  workTauntMessage.setText(message.getContent());
                });

            return null;
          }
        };

    Thread workThread = new Thread(workTauntGPT);
    workThread.start();
  }

  public void goProgress() {
    App.setUi(AppPanel.PROGRESS);
  }

  public void workReturn() {
    SceneManager.setPrevious(AppPanel.MAIN_ROOM);
    App.setUi(AppPanel.MAIN_ROOM);
  }

  // if sand is inside the inventory, furnace is able to be used to melt sand into glass (3)
  public void meltSand() {
    if (GameState.inventory.contains(2)) {
      GameState.inventory.add(3);
    }
  }

  // if the necessary items are inside the inventory, craft window, control panel, or both
  public void craftItems() {
    if (GameState.inventory.contains(3) && GameState.inventory.contains(0)) {
      GameState.inventory.add(4);
    }
    if (GameState.inventory.contains(1) && GameState.inventory.contains(0)) {
      GameState.inventory.add(5);
    }
  }

  // closes taunt by pressing cross at top right of screen
  public void closeMessage() {
    workBackground.setVisible(false);
    workTauntMessage.setVisible(false);
    cross.setVisible(false);
    crossOne.setVisible(false);
    crossTwo.setVisible(false);
    voiceLabel.setVisible(false);
  }

  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
    chatCompletionRequest.addMessage(msg);
    try {
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      chatCompletionRequest.addMessage(result.getChatMessage());
      return result.getChatMessage();
    } catch (ApiProxyException e) {

      workTauntMessage.setText(
          "Sorry, there was a problem generating a response. Please try restarting the"
              + " application.");
      e.printStackTrace();
      return null;
    }
  }

  public void workReturnDoorLight() {
    workReturnDoor.setFill(Color.valueOf("835339"));
    centralLabel.setVisible(true);
  }

  public void workReturnDoorNormal() {
    workReturnDoor.setFill(Color.valueOf("653920"));
    centralLabel.setVisible(false);
  }

  // logic checks all possible states for the inventory; depending on what is inside the inventory,
  // different labels are shown above the workbench: 'you cannot craft anything yet,' 'craft
  // window,' 'craft control panel,' 'craft window and control panel'
  public void benchLight() {
    bench.setFill(Color.valueOf("63b4ff"));
    if (!GameState.inventory.contains(0)
        || (!GameState.inventory.contains(1) && !GameState.inventory.contains(3))) {
      craftNone.setVisible(true);
    } else if (GameState.inventory.contains(0)
        && GameState.inventory.contains(1)
        && GameState.inventory.contains(3)
        && !GameState.inventory.contains(4)
        && !GameState.inventory.contains(5)) {
      craftAll.setVisible(true);
    } else if (GameState.inventory.contains(0)
        && GameState.inventory.contains(1)
        && !GameState.inventory.contains(5)) {
      craftPanel.setVisible(true);
    } else if (GameState.inventory.contains(0)
        && GameState.inventory.contains(3)
        && !GameState.inventory.contains(4)) {

      craftWindow.setVisible(true);
    }
  }

  public void benchNormal() {
    bench.setFill(Color.valueOf("1e90ff"));
    craftWindow.setVisible(false);
    craftAll.setVisible(false);
    craftPanel.setVisible(false);
    craftNone.setVisible(false);
  }

  // logic checks inventory: if sand is not present, ???? label is shown; if sand is present, melt
  // sand is shown, and if glass is present, then nothing is shown
  public void furnaceLight() {
    if (GameState.inventory.contains(2) && !GameState.inventory.contains(3)) {
      sandLabel.setVisible(true);
    } else if (!GameState.inventory.contains(2)) {
      questionLabel.setVisible(true);
    }
    furnace.setFill(Color.valueOf("7f858a"));
  }

  // removes labels and sets default color
  public void furnaceDark() {
    if (GameState.inventory.contains(2)) {
      sandLabel.setVisible(false);
    } else {
      questionLabel.setVisible(false);
    }
    furnace.setFill(Color.valueOf("5a636b"));
  }

  public void activateProgressGlow() {
    progressButton.setEffect(GameState.glowBright);
  }

  public void deactivateProgressGlow() {
    progressButton.setEffect(GameState.glowDim);
  }
}
