package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class CentralController {
  @FXML private Polygon processMachine;
  @FXML private Label counter;
  @FXML private ImageView outsideDoor;
  @FXML private ImageView storage;
  @FXML private ImageView progressButton;
  @FXML private ImageView rootInitial;
  @FXML private ImageView rootOne;
  @FXML private ImageView rootTwo;
  @FXML private ImageView rootThree;
  @FXML private ImageView processMachineImage;

  public void goOutside() {
    SceneManager.setPrevious(AppPanel.MAIN_ROOM);
    App.setUi(AppPanel.CRASHLAND);
  }

  public void goProgress() {
    SceneManager.setPrevious(AppPanel.MAIN_ROOM);
    App.setUi(AppPanel.PROGRESS);
  }

  public void goStorage() {
    App.setUi(AppPanel.STORAGE);
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
    outsideDoor.setEffect(GameState.glowBright);
  }

  public void deactivateDoorGlow() {
    outsideDoor.setEffect(GameState.glowDim);
  }

  public void activateProcessMachineGlow() {
    processMachineImage.setEffect(GameState.glowBright);
  }

  public void deactivateProcessMachineGlow() {
    processMachineImage.setEffect(GameState.glowDim);
  }

  public void activateStorageGlow() {
    storage.setEffect(GameState.glowBright);
  }

  public void deactivateStorageGlow() {
    storage.setEffect(GameState.glowDim);
  }
}
