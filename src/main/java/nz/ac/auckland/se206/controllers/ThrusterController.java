package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionManager.MISSION;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.ThrusterButtons.BottomLeftButton;
import nz.ac.auckland.se206.ThrusterButtons.BottomRightButton;
import nz.ac.auckland.se206.ThrusterButtons.TopLeftButton;
import nz.ac.auckland.se206.ThrusterButtons.TopRightButton;
import nz.ac.auckland.se206.TreeAvatar;

public class ThrusterController {

  @FXML private ImageView bottomLeftUnclicked;
  @FXML private ImageView bottomLeftClicked;
  @FXML private ImageView bottomRightUnclicked;
  @FXML private ImageView bottomRightClicked;
  @FXML private ImageView topLeftUnclicked;
  @FXML private ImageView topLeftClicked;
  @FXML private ImageView topRightUnclicked;
  @FXML private ImageView topRightClicked;
  @FXML private Button returnOutside;
  @FXML private ImageView miniTree;
  private int buttonActivationCounter = 0;
  public static int isGameActive = 0;

  public void initialize() {}

  public void setBottomLeftVisible() {
    if (isGameActive == 1) {
      if (BottomLeftButton.getCycleNumber() != GameState.getRandomColorNumber()) {
        BottomLeftButton.timeline.play();
        bottomLeftUnclicked.setVisible(true);
      } else {
        BottomLeftButton.timeline.pause();
        buttonActivationCounter++;
        isMissionComplete();
      }
    }
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

  public void setBottomRightVisible() {
    if (isGameActive == 1) {
      if (BottomRightButton.getCycleNumber() != GameState.getRandomColorNumber()) {
        BottomRightButton.timeline.play();
        bottomRightUnclicked.setVisible(true);
      } else {
        BottomRightButton.timeline.pause();
        buttonActivationCounter++;
        isMissionComplete();
      }
    }
  }

  public void setTopLeftInvisible() {
    if (isGameActive == 1) {
      TopLeftButton.timeline.pause();
      topLeftUnclicked.setVisible(false);
    }
  }

  public void setTopLeftVisible() {
    if (isGameActive == 1) {
      if (TopLeftButton.getCycleNumber() != GameState.getRandomColorNumber()) {
        topLeftUnclicked.setVisible(true);
        TopLeftButton.timeline.play();
      } else {
        TopLeftButton.timeline.pause();
        buttonActivationCounter++;
        isMissionComplete();
      }
    }
  }

  public void setTopRightInvisible() {
    if (isGameActive == 1) {
      TopRightButton.timeline.pause();
      topRightUnclicked.setVisible(false);
    }
  }

  public void setTopRightVisible() {
    if (isGameActive == 1) {
      if (TopRightButton.getCycleNumber() != GameState.getRandomColorNumber()) {
        TopRightButton.timeline.play();
        topRightUnclicked.setVisible(true);
      } else {
        TopRightButton.timeline.pause();
        buttonActivationCounter++;
        isMissionComplete();
      }
    }
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
    }
  }

  public void goOutside() {
    App.setUi(AppPanel.OUTSIDE);
  }

  public void isMissionComplete() {
    if (buttonActivationCounter == 4) {
      GameState.missionManager.getMission(MISSION.THRUSTER).increaseStage();
      GameState.progressBarGroup.updateProgressTwo(MISSION.THRUSTER);
      System.out.println("Thruster Mission Complete");
    }
  }

  public void goChat() {
    TreeAvatar.treeFlash.pause();
    TreeAvatar.deactivateTreeGlow();
    App.setUi(AppPanel.CHAT);
    SceneManager.setPrevious(AppPanel.THRUSTER);
  }

  public void miniTreeGlow() {
    miniTree.setEffect(GameState.glowBright);
  }

  public void miniTreeDim() {
    miniTree.setEffect(GameState.glowDim);
  }
}
