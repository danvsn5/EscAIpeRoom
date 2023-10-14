package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionManager.MISSION;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.TreeAvatar;
import nz.ac.auckland.se206.buttons.BottomLeftButton;
import nz.ac.auckland.se206.buttons.BottomRightButton;
import nz.ac.auckland.se206.buttons.TopLeftButton;
import nz.ac.auckland.se206.buttons.TopRightButton;

public class ThrusterController {

  public static int buttonActivationCounter = 0;
  public static int isGameActive = 0;

  @FXML private ImageView bottomLeftUnclicked;
  @FXML private ImageView bottomLeftClicked;
  @FXML private ImageView bottomRightUnclicked;
  @FXML private ImageView bottomRightClicked;
  @FXML private ImageView topLeftUnclicked;
  @FXML private ImageView topLeftClicked;
  @FXML private ImageView topRightUnclicked;
  @FXML private ImageView topRightClicked;
  @FXML private Button returnOutside;
  @FXML private ImageView progressButton;
  @FXML private ImageView miniTree;
  @FXML private Button repairButton;
  @FXML private Button completeButton;

  @FXML private ImageView thrusterRoot;
  @FXML private Polygon rootCollisionBox1;
  @FXML private Polygon rootCollisionBox2;
  @FXML private Polygon rootCollisionBox3;

  public void initialize() {}

  public void setBottomLeftVisible() { // sets the bottom left button to visible
    if (isGameActive == 1) { // checks if the game is active
      if (BottomLeftButton.getCycleNumber() != GameState.getRandomColorNumber()) {
        BottomLeftButton.timeline.play();
        bottomLeftUnclicked.setVisible(true); // sets the button to visible
      } else {
        BottomLeftButton.timeline.pause();
        buttonActivationCounter++; // increments the button activation counter
      }
    }
    checkCompletion();
  }

  public void goProgress() {
    SceneManager.setPrevious(AppPanel.THRUSTER);
    App.setUi(AppPanel.PROGRESS);
  }

  public void setBottomLeftInvisible() {
    if (isGameActive == 1) {
      BottomLeftButton.timeline.pause();
      bottomLeftUnclicked.setVisible(false);
    }
  }

  public void setBottomRightInvisible() {
    if (isGameActive == 1) {
      BottomRightButton.timeline.pause();
      bottomRightUnclicked.setVisible(false);
    }
  }

  public void setBottomRightVisible() { // sets the bottom right button to visible
    if (isGameActive == 1) { // checks if the game is active
      if (BottomRightButton.getCycleNumber() != GameState.getRandomColorNumber()) {
        BottomRightButton.timeline.play();
        bottomRightUnclicked.setVisible(true); // sets the button to visible
      } else {
        BottomRightButton.timeline.pause();
        buttonActivationCounter++; // increments the button activation counter
      }
    }
    checkCompletion();
  }

  public void setTopLeftInvisible() {
    if (isGameActive == 1) {
      TopLeftButton.timeline.pause();
      topLeftUnclicked.setVisible(false);
    }
  }

  public void setTopLeftVisible() { // sets the top left button to visible
    if (isGameActive == 1) { // checks if the game is active
      if (TopLeftButton.getCycleNumber() != GameState.getRandomColorNumber()) {
        topLeftUnclicked.setVisible(true); // sets the button to visible
        TopLeftButton.timeline.play();
      } else {
        TopLeftButton.timeline.pause();
        buttonActivationCounter++; // increments the button activation counter
      }
    }
    checkCompletion();
  }

  public void setTopRightInvisible() {
    if (isGameActive == 1) {
      TopRightButton.timeline.pause();
      topRightUnclicked.setVisible(false);
    }
  }

  public void setTopRightVisible() { // sets the top right button to visible
    if (isGameActive == 1) { // checks if the game is active
      if (TopRightButton.getCycleNumber() != GameState.getRandomColorNumber()) {
        TopRightButton.timeline.play();
        topRightUnclicked.setVisible(true); // sets the button to visible
      } else {
        TopRightButton.timeline.pause();
        buttonActivationCounter++; // increments the button activation counter
      }
    }
    checkCompletion();
  }

  public void beginRepairs() {

    // Mission stage is set to 2 when the player has collected the blueprint and has solved the
    // color puzzle. Only once both steps have been completed can the player begin the thruster
    // mini-game

    if (GameState.missionManager.getMission(MISSION.THRUSTER).getStage() == 0) {
      SceneManager.showDialog(
          "Info", "Thruster Repair", "You are missing the blueprint and the colour key!");
    } else if (GameState.missionManager.getMission(MISSION.THRUSTER).getStage() == 1) {
      SceneManager.showDialog("Info", "Thruster Repair", "You are missing the colour key!");
    }

    if (buttonActivationCounter == 0
        && GameState.missionManager.getMission(MISSION.THRUSTER).getStage() == 2) {

      isGameActive = 1;
      BottomRightButton.timeline.setCycleCount(360);
      BottomRightButton.timeline.play();
      BottomLeftButton.timeline.setCycleCount(360);
      BottomLeftButton.timeline.play();
      TopLeftButton.timeline.setCycleCount(360);
      TopLeftButton.timeline.play();
      TopRightButton.timeline.setCycleCount(360);
      TopRightButton.timeline.play();
      repairButton.setVisible(false);
    }
  }

  public void goOutside() {
    App.setUi(AppPanel.OUTSIDE);
  }

  public void goChat() {
    TreeAvatar.treeFlash.pause();
    // TreeAvatar.deactivateTreeGlow();
    App.setUi(AppPanel.CHAT);
    SceneManager.setPrevious(AppPanel.THRUSTER);
  }

  public void miniTreeGlow() {
    miniTree.setEffect(GameState.glowBright);
  }

  public void miniTreeDim() {
    miniTree.setEffect(GameState.glowDim);
  }

  public void activateProgressGlow() {
    progressButton.setEffect(GameState.glowBright);
  }

  public void deactivateProgressGlow() {
    progressButton.setEffect(GameState.glowDim);
  }

  public void activateRootGlow() {
    rootCollisionBox1.setOpacity(1);
    rootCollisionBox2.setOpacity(1);
    rootCollisionBox3.setOpacity(1);
  }

  public void deactivateRootGlow() {
    rootCollisionBox1.setOpacity(0);
    rootCollisionBox2.setOpacity(0);
    rootCollisionBox3.setOpacity(0);
  }

  public void checkCompletion() {
    if (buttonActivationCounter == 4) {
      completeButton.setDisable(false);
      completeButton.setVisible(true);
    }
  }

  public void completeRepair() {
    SceneManager.showDialog("Info", "Thruster", "You have repaired the thrusters of the ship!");
    GameState.missionManager.getMission(MISSION.THRUSTER).increaseStage();
    GameState.progressBarGroup.updateProgressTwo(MISSION.THRUSTER);
    System.out.println("Thruster Mission Complete");
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#completeGame").setVisible(true);
    SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#thruster1").setVisible(false);
    SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#thruster1").setDisable(true);
    SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#thruster2").setVisible(false);
    SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#thruster2").setDisable(true);
    completeButton.setDisable(true);
    completeButton.setVisible(false);
    SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#outsideImage").setVisible(true);
    SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#outsideBrokenImage").setVisible(false);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#completeImage").setVisible(true);
  }
}
