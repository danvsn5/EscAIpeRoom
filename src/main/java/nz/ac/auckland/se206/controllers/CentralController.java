package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
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

  public void goWorkshop() {
    SceneManager.setPrevious(AppPanel.WORK);
    App.setUi(AppPanel.WORK);
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

      window.setOpacity(0);
      window.setDisable(true);
    }
  }

  public void addFuel() {
    if (GameState.inventory.contains(8)) {
      GameState.missionManager.getMission(MISSION.FUEL).increaseStage();
      GameState.progressBarGroup.updateProgressOne(MISSION.FUEL);
      System.out.println("Fuel Mission Complete");
      GameState.isFirstMissionCompleted = true;
      GameState.inventory.remove(GameState.inventory.indexOf(8));
      fuelTank.setOpacity(0);
      fuelTank.setDisable(true);
    } else {
      System.out.println("Fuel not collected");
    }
  }

  // if window and control panel are fixed, then game can be completed by pressing red button
  public void goHome() {
    if (GameState.inventory.contains(6) && GameState.inventory.contains(7)) {
      App.setUi(AppPanel.WIN);
    }
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

  public void goChat() {
    TreeAvatar.treeFlash.pause();
    TreeAvatar.deactivateTreeGlow();
    App.setUi(AppPanel.CHAT);
  }

  public void miniTreeGlow() {
    miniTree.setEffect(GameState.glowBright);
  }

  public void miniTreeDim() {
    miniTree.setEffect(GameState.glowDim);
  }
}
