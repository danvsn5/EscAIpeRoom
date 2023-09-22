package nz.ac.auckland.se206.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionManager.MISSION;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.TreeAvatar;

public class CentralController {
  @FXML private Rectangle outsideDoorRectangle;
  @FXML private Label counter;
  @FXML private ImageView outsideDoor;
  @FXML private ImageView storage;
  @FXML private ImageView progressButton;
  @FXML private ImageView rootInitial;
  @FXML private ImageView rootOne;
  @FXML private ImageView rootTwo;
  @FXML private ImageView rootThree;
  @FXML private ImageView outsideDoorImage;
  @FXML private ImageView window;
  @FXML private ImageView chest;
  @FXML private ImageView miniTree;
  @FXML private ImageView fuelTank;
  @FXML private ImageView controller;
  @FXML private static ImageView completeGame;
  private static int winFlashState = 0;
  private static Timeline winFlash =
      new Timeline(new KeyFrame(Duration.millis(500), e -> flashWinButton()));

  @FXML private Rectangle guideWindow;
  @FXML private Label guideLabel;
  @FXML private Button okButton;

  public void goOutside() {
    SceneManager.setPrevious(AppPanel.MAIN_ROOM);
    App.setUi(AppPanel.OUTSIDE);
  }

  public void goProgress() {
    SceneManager.setPrevious(AppPanel.MAIN_ROOM);
    App.setUi(AppPanel.PROGRESS);
  }

  public void goStorage() {
    App.setUi(AppPanel.STORAGE);
  }

  public void goWin() {
    LaunchController.timer.setFinish();
    winFlash.stop();
    App.setUi(AppPanel.WIN);
  }

  // if inventory contains the necessary items, fixes the window and control panel and changes
  // visibility of assets
  public void repairWindow() {
    if (GameState.inventory.contains(3)) {
      GameState.missionManager.getMission(MISSION.WINDOW).increaseStage();
      GameState.progressBarGroup.updateProgressOne(MISSION.WINDOW);

      System.out.println("Window Mission Complete");
      System.out.println(GameState.missionManager.getMission(MISSION.WINDOW).getStage());

      GameState.isFirstMissionCompleted = true;

      System.out.println(GameState.isFirstMissionCompleted);
      System.out.println("WindowFixed");

      TreeAvatar.startFlashTree();

      window.setOpacity(0);
      window.setDisable(true);
      activateBlueprint();
      activateChest();
      SceneManager.showDialog("Info", "Window fixed", "Mission accomplished");
    } else if (GameState.inventory.contains(2)) {
      SceneManager.showDialog("Info", "Broken Window", "A large crack is inside the window!");
    } else {
      SceneManager.showDialog("Info", "Broken Window", "A large crack is inside the window!");
    }
  }

  public void addFuel() {
    if (GameState.inventory.contains(8)) {
      GameState.missionManager.getMission(MISSION.FUEL).increaseStage();
      GameState.progressBarGroup.updateProgressOne(MISSION.FUEL);
      System.out.println("Fuel Mission Complete");
      GameState.isFirstMissionCompleted = true;
      GameState.inventory.remove(GameState.inventory.indexOf(8));

      TreeAvatar.startFlashTree();

      fuelTank.setOpacity(0);
      fuelTank.setDisable(true);
      activateBlueprint();
      activateChest();
      SceneManager.showDialog("Info", "Fuel added", "Mission accomplished");
    } else {
      SceneManager.showDialog("Info", "No Fuel", "Internal fuel tank is empty!");
    }
  }

  public void fixController() {
    if (GameState.missionManager.getMission(MISSION.CONTROLLER).getStage() != 3) {
      SceneManager.showDialog(
          "Info", "Broken Control Panel", "The control panel for the ship is broken!");
    } else if (GameState.missionManager.getMission(MISSION.CONTROLLER).getStage() == 3) {
      SceneManager.showDialog("Info", "Controller fixed", "Mission accomplished");
      GameState.missionManager.getMission(MISSION.CONTROLLER).increaseStage();
      GameState.progressBarGroup.updateProgressTwo(MISSION.CONTROLLER);
      System.out.println("Controller Mission Complete");
      completeGame.setVisible(true);
      beginWinFlash();
      GameState.isSecondMissionCompleted = true;
    }
  }

  // if window and control panel are fixed, then game can be completed by pressing red button
  public void goHome() {
    if (GameState.inventory.contains(6) && GameState.inventory.contains(7)) {
      App.setUi(AppPanel.WIN);
    }
  }

  private void activateBlueprint() {
    if (!GameState.firstRiddleSolved || GameState.missionList.contains(3)) {
      return;
    }
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#blueprint").setVisible(true);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#blueprint").setDisable(false);
  }

  private void activateChest() {
    if (!GameState.firstRiddleSolved || GameState.missionList.contains(4)) {
      return;
    }
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#chest").setVisible(true);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#chest").setDisable(false);
  }

  public void activateProgressGlow() {
    progressButton.setEffect(GameState.glowBright);
  }

  public void deactivateProgressGlow() {
    progressButton.setEffect(GameState.glowDim);
  }

  public void activateDoorGlow() {
    outsideDoorImage.setEffect(GameState.glowBright);
    outsideDoor.setEffect(GameState.glowBright);
  }

  public void deactivateDoorGlow() {
    outsideDoorImage.setEffect(GameState.glowDim);
    outsideDoor.setEffect(GameState.glowDim);
  }

  public void activateFuelTankGlow() {
    fuelTank.setEffect(GameState.glowBright);
  }

  public void deactivateFuelTankGlow() {
    fuelTank.setEffect(GameState.glowDim);
  }

  public void activateStorageGlow() {
    storage.setEffect(GameState.glowBright);
  }

  public void deactivateStorageGlow() {
    storage.setEffect(GameState.glowDim);
  }

  public void activateWindowGlow() {
    window.setEffect(GameState.glowBright);
  }

  public void deactivateWindowGlow() {
    window.setEffect(GameState.glowDim);
  }

  public void activateControllerGlow() {
    controller.setEffect(GameState.glowBright);
  }

  public void deactivateControllerGlow() {
    controller.setEffect(GameState.glowDim);
  }

  public void goChat() {
    TreeAvatar.treeFlash.pause();
    TreeAvatar.deactivateTreeGlow();

    if (GameState.isFirstMissionCompleted
        && GameState.missionList.contains(1)
        && GameState.missionList.contains(4)) {
      ((TextArea) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatTextArea"))
          .appendText(
              "You have repaired the window... Well done. You still cannot leave however, as the"
                  + " thrusters are still damaged. In amongst your ship, I have hidden a blueprint"
                  + " that should help you fix the thrusters");
    }

    SceneManager.setPrevious(AppPanel.MAIN_ROOM);
    App.setUi(AppPanel.CHAT);
  }

  public void miniTreeGlow() {
    miniTree.setEffect(GameState.glowBright);
  }

  public void miniTreeDim() {
    miniTree.setEffect(GameState.glowDim);
  }

  public void okBtnPressed() {
    guideWindow.setVisible(false);
    guideLabel.setVisible(false);
    okButton.setVisible(false);
    okButton.setDisable(true);
  }

  public void activateWinGlow() {
    completeGame.setEffect(GameState.glowBright);
  }

  public void deactivateWinGlow() {
    completeGame.setEffect(GameState.glowDim);
  }

  public static void beginWinFlash() {
    winFlash.setCycleCount(Timeline.INDEFINITE);
    winFlash.play();
  }

  public static void flashWinButton() {
    if (winFlashState == 0) {
      completeGame.setEffect(GameState.glowBright);
      winFlashState = 1;
    } else {
      completeGame.setEffect(GameState.glowDim);
      winFlashState = 0;
    }
  }
}
