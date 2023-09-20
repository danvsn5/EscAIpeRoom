package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class CrashLandController {
  @FXML private Polygon forest;
  @FXML private Polygon shuttle;
  @FXML private Polygon thruster;
  @FXML private ImageView forestImage;
  @FXML private ImageView shuttleImage;
  @FXML private ImageView thrusterImage;

  public void goForest() {
    App.setUi(AppPanel.OUTSIDE);
  }

  public void goInside() {
    App.setUi(AppPanel.MAIN_ROOM);
  }

  public void goThruster() {
    App.setUi(AppPanel.THRUSTER);
  }

  public void activateForestGlow() {
    forestImage.setEffect(GameState.glowBright);
  }

  public void deactivateForestGlow() {
    forestImage.setEffect(GameState.glowDim);
  }

  public void activateShuttleGlow() {
    shuttleImage.setEffect(GameState.glowBright);
  }

  public void deactivateShuttleGlow() {
    shuttleImage.setEffect(GameState.glowDim);
  }

  public void activateThrusterGlow() {
    thrusterImage.setEffect(GameState.glowBright);
  }

  public void deactivateThrusterGlow() {
    thrusterImage.setEffect(GameState.glowDim);
  }
}
