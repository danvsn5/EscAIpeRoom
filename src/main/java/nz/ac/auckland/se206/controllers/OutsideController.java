package nz.ac.auckland.se206.controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionManager.MISSION;
// import nz.ac.auckland.se206.MissionManager.MISSION;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.TreeAvatar;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

public class OutsideController {
  @FXML private ImageView outsideImage;
  @FXML private ImageView outsideBrokenImage;
  @FXML private ImageView returnShip;
  @FXML private Label counter;
  @FXML private Polygon wiseTree;
  @FXML private Polygon thruster1;
  @FXML private Polygon thruster2;
  @FXML private Polygon shipDoor1;
  @FXML private Polygon shipDoor2;
  @FXML private ImageView progressButton;
  @FXML private ImageView root1;
  @FXML private ImageView root2;
  @FXML private ImageView root3;
  @FXML private ImageView root4;
  @FXML private ImageView miniTree;
  @FXML private ImageView ship;

  @FXML private Polygon sand;
  @FXML private ImageView sandInfo;
  @FXML private Label collectedLabel;
  @FXML private Label collectedTitle;

  private int thrusterPuzzleGenerate = 0;
  private ChatMessage gptMessage;

  // displays counter on panel and constantly checks if the riddle has been solved. If riddle was
  // solved correctly, and sand is currently NOT in the inventory, then the sand appears inside the
  // panel.
  public void goProgress() {
    SceneManager.setPrevious(AppPanel.OUTSIDE);
    App.setUi(AppPanel.PROGRESS);
  }

  public void goToShip() {
    App.setUi(AppPanel.MAIN_ROOM);
  }

  public void outsideReturn() {
    SceneManager.setPrevious(AppPanel.MAIN_ROOM);
    App.setUi(AppPanel.MAIN_ROOM);
  }

  public void goThruster() {
    if (GameState.missionList.contains(4)) {
      if (thrusterPuzzleGenerate == 0 && GameState.isFirstMissionCompleted == true) {

        Task<Void> riddleSecondCall =
            new Task<Void>() {

              @Override
              protected Void call() throws Exception {

                System.out.println("this code is working");

                // switch case from 1 to 4 based on the variable GameState.randomColourNumber
                // 1: purple    2: blue     3: red    4: green
                switch (GameState.randomColorNumber) {
                  case 1:
                    gptMessage =
                        runGpt(
                            new ChatMessage(
                                "user", GptPromptEngineering.getThrusterPuzzle("purple")));
                                thrusterPuzzleGenerate = 1;
                    break;
                  case 2:
                    gptMessage =
                        runGpt(
                            new ChatMessage("user", GptPromptEngineering.getThrusterPuzzle("red")));
                            thrusterPuzzleGenerate = 1;
                    break;
                  case 3:
                    gptMessage =
                        runGpt(
                            new ChatMessage(
                                "user", GptPromptEngineering.getThrusterPuzzle("blue")));
                                thrusterPuzzleGenerate = 1;
                    break;
                  case 4:
                    gptMessage =
                        runGpt(
                            new ChatMessage(
                                "user", GptPromptEngineering.getThrusterPuzzle("green")));
                                thrusterPuzzleGenerate = 1;
                    break;
                }
                Platform.runLater(() -> appendChatMessage(gptMessage));

                System.out.println(gptMessage.getContent());

                return null;
              }
            };

        Thread secondRiddleThread = new Thread(riddleSecondCall);
        secondRiddleThread.start();
        TreeAvatar.treeFlash.play();
      }
      App.setUi(AppPanel.THRUSTER);
    }
  }

  // public void thrusterError() {
  //   if (ThrusterController.buttonActivationCounter != 4) {
  //     SceneManager.showDialog("Info", "Thruster", "The thrusters of your ship are damaged!");
  //   } else {
  //     SceneManager.showDialog("Info", "Thruster", "You have repaired the thrusters of the
  // ship!");
  //     GameState.missionManager.getMission(MISSION.THRUSTER).increaseStage();
  //     GameState.progressBarGroup.updateProgressTwo(MISSION.THRUSTER);
  //     System.out.println("Thruster Mission Complete");
  //     SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#completeGame").setVisible(true);
  //     thrusterWarning.setVisible(false);
  //   }
  // }

  // there are two types of methods below: Light and Dark/Normal. On hover over with mouse, Light
  // method is invoked: the color of the selected object becomes lighter and a label becomes
  // visible, indicating it is
  // clickble. Once mouse is moved from object, color returns to original and the label is made
  // invisible with Dark/Normal method
  // invokation.

  public void openRiddle() throws ApiProxyException {
    SceneManager.setPrevious(AppPanel.OUTSIDE);
    TreeAvatar.treeFlash.pause();
    TreeAvatar.deactivateTreeGlow();
    TreeAvatar.setTreeVisible();
    if (ChatController.seenFirstMessage == 0) {
      ChatController.seenFirstMessage = 1;
    }

    App.setUi(AppPanel.CHAT);
  }

  public void activateProgressGlow() {
    progressButton.setEffect(GameState.glowBright);
  }

  public void deactivateProgressGlow() {
    progressButton.setEffect(GameState.glowDim);
  }

  public void goChat() {
    TreeAvatar.treeFlash.pause();
    TreeAvatar.deactivateTreeGlow();
    App.setUi(AppPanel.CHAT);
    SceneManager.setPrevious(AppPanel.OUTSIDE);
  }

  public void miniTreeGlow() {
    miniTree.setEffect(GameState.glowBright);
  }

  public void miniTreeDim() {
    miniTree.setEffect(GameState.glowDim);
  }

  public void activateShipGlow() {
    ship.setEffect(GameState.glowBright);
    shipDoor1.setOpacity(1);
    shipDoor2.setOpacity(1);
  }

  public void deactivateShipGlow() {
    ship.setEffect(GameState.glowDim);
    shipDoor1.setOpacity(0);
    shipDoor2.setOpacity(0);
  }

  public void activateTreeGlow() {
    wiseTree.setOpacity(1);
  }

  public void deactivateTreeGlow() {
    wiseTree.setOpacity(0);
  }

  public void activateThrusterGlow() {
    if (GameState.missionList.contains(4)) {
      thruster1.setOpacity(1);
      thruster2.setOpacity(1);
    }
  }

  public void deactivateThrusterGlow() {
    if (GameState.missionList.contains(4)) {
      thruster1.setOpacity(0);
      thruster2.setOpacity(0);
    }
  }

  public void activateSandGlow() {
    if (GameState.isBucketCollected && !GameState.isSandCollected) {
      sand.setDisable(false);
      sand.setOpacity(1);
    }
  }

  public void deactivateSandGlow() {
    sand.setOpacity(0);
  }

  public void collectSand() {
    if (GameState.isBucketCollected && !GameState.isSandCollected) {
      GameState.inventory.add(2);
    GameState.missionManager.getMission(MISSION.WINDOW).increaseStage();
    GameState.progressBarGroup.updateProgressOne(MISSION.WINDOW);
    collectedTitle.setText("Sand Collected");
    collectedLabel.setText("A pile of sand, can be melted into glass.");
    collectedLabel.setVisible(true);
    collectedTitle.setVisible(true);
    sandInfo.setVisible(true);
    sand.setDisable(true);
    sand.setVisible(false);
    System.out.println("Sand collected");
    GameState.isSandCollected = true;
    }
  }

  /* This method closes all info panel in this page */
  public void exitInfo() {
    collectedLabel.setVisible(false);
    sandInfo.setVisible(false);
    collectedTitle.setVisible(false);
  }

  /* ======================================= GPT Helper Methods ======================================= */
  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
    ChatController.chatCompletionRequest.addMessage(msg);
    try {
      ChatCompletionResult chatCompletionResult = ChatController.chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      ChatController.chatCompletionRequest.addMessage(result.getChatMessage());
      result.getChatMessage().setRole("Wise Mystical Tree");
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

  private void appendChatMessage(ChatMessage msg) {
    // chatTextArea.appendText(msg.getRole() + ": " + msg.getContent() + "\n\n");
    ((TextArea) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatTextArea"))
        .appendText("\n\n" + "Wise Ancient Tree: " + msg.getContent() + "\n\n");
    ((Label) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatLabel")).setText(msg.getContent());
  }
}
