package nz.ac.auckland.se206.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionManager.MISSION;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.TreeAvatar;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class OutsideController {
  @FXML private ImageView returnShip;
  @FXML private Label counter;
  @FXML private Polygon wiseTree;
  @FXML private Polygon thruster;
  @FXML private ImageView progressButton;
  @FXML private ImageView rootInitial;
  @FXML private ImageView rootOne;
  @FXML private ImageView rootTwo;
  @FXML private ImageView rootThree;
  @FXML private ImageView miniTree;
  @FXML private ImageView treeImage;
  @FXML private ImageView ship;
  @FXML private ImageView thrusterImage;
  @FXML private ImageView thrusterWarning;

  public void initialize() {}

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
    if (GameState.missionList.contains(4)
        && GameState.isFirstMissionCompleted == true
        && GameState.missionManager.getMission(MISSION.THRUSTER).getStage() == 1) {
      TreeAvatar.startFlashTree();
      App.setUi(AppPanel.THRUSTER);
      ((TextArea) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatTextArea"))
          .appendText(ChatController.secondMissionPuzzle.getContent());

    } else if (GameState.missionList.contains(4)) {
      App.setUi(AppPanel.THRUSTER);
    }
  }

  public void showTrusterError() { // shows the message when clicked.
    if (ThrusterController.buttonActivationCounter != 4) { // if the thruster is not fixed
      SceneManager.showDialog("Info", "Thruster", "The thrusters of your ship are damaged!");
    } else { // if the thruster is fixed
      SceneManager.showDialog("Info", "Thruster", "You have repaired the thrusters of the ship!");
      GameState.missionManager.getMission(MISSION.THRUSTER).increaseStage();
      GameState.progressBarGroup.updateProgressTwo(MISSION.THRUSTER); // updates the progress bar
      System.out.println("Thruster Mission Complete"); // prints to console
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#completeGame").setVisible(true);
      thrusterWarning.setVisible(false);
    }
  }

  public void openRiddle() throws ApiProxyException { // opens the riddle
    SceneManager.setPrevious(AppPanel.OUTSIDE);
    TreeAvatar.treeFlash.pause();
    TreeAvatar.deactivateTreeGlow(); // deactivates the glow
    TreeAvatar.setTreeVisible(); // sets the tree visible
    if (GameState.inventory.contains(-2) && ChatController.seenFirstMessage == 0) {

      // creates a new thread to speak the first message
      Task<Void> speakFirstMessage =
          new Task<Void>() {

            @Override
            protected Void call() throws Exception {
              GameState.textToSpeech.speak(ChatController.firstMesage.getContent());

              return null;
            }
          };

      Thread typeInThread = new Thread(speakFirstMessage);
      typeInThread.start(); // starts the thread
      ChatController.seenFirstMessage = 1; // sets the seenFirstMessage to 1
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
  }

  public void deactivateShipGlow() {
    ship.setEffect(GameState.glowDim);
  }

  public void activateTreeGlow() {
    treeImage.setEffect(GameState.glowBright);
  }

  public void deactivateTreeGlow() {
    treeImage.setEffect(GameState.glowDim);
  }

  public void activateThrusterGlow() {
    if (GameState.missionList.contains(4)) {
      thrusterImage.setEffect(GameState.glowBright);
    }
  }

  public void deactivateThrusterGlow() {
    if (GameState.missionList.contains(4)) {
      thrusterImage.setEffect(GameState.glowDim);
    }
  }

  public void activateThrusterErrorGlow() {
    thrusterWarning.setEffect(GameState.glowBright);
  }

  public void deactivateThrusterErrorGlow() {
    thrusterWarning.setEffect(GameState.glowDim);
  }
}
