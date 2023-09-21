package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class CrashLandController {
  @FXML private Polygon thruster;
  @FXML private Polygon shuttlePolygon;
  @FXML private ImageView shuttle;
  @FXML private ImageView forest;
  @FXML private ImageView workRoom;
  @FXML private ImageView progressButton;
  @FXML private ImageView thrusterImage;
  @FXML private ImageView shuttleImage;
  @FXML private Label counter;

  public void goForest() {
    App.setUi(AppPanel.OUTSIDE);
  }

  public void goInside() {
    App.setUi(AppPanel.MAIN_ROOM);
  }

  public void goThruster() {
    App.setUi(AppPanel.THRUSTER);
  }

  public void goProgress() {
    SceneManager.setPrevious(AppPanel.CRASHLAND);
    App.setUi(AppPanel.PROGRESS);
  }

  public void goWorkRoom() {
    App.setUi(AppPanel.WORK);
  }

  public void activateProgressGlow() {
    progressButton.setEffect(GameState.glowBright);
  }

  public void deactivateProgressGlow() {
    progressButton.setEffect(GameState.glowDim);
  }

  public void activateForestGlow() {
    forest.setEffect(GameState.glowBright);
  }

  public void deactivateForestGlow() {
    forest.setEffect(GameState.glowDim);
  }

  public void activateShuttleGlow() {
    shuttleImage.setEffect(GameState.glowBright);
    shuttle.setEffect(GameState.glowBright);
  }

  public void deactivateShuttleGlow() {
    shuttleImage.setEffect(GameState.glowDim);
    shuttle.setEffect(GameState.glowDim);
  }

  public void activateThrusterGlow() {
    thrusterImage.setEffect(GameState.glowBright);
  }

  public void deactivateThrusterGlow() {
    thrusterImage.setEffect(GameState.glowDim);
  }

  public void activateWorkRoomGlow() {
    workRoom.setEffect(GameState.glowBright);
  }

  public void deactivateWorkRoomGlow() {
    workRoom.setEffect(GameState.glowDim);
  }
}
