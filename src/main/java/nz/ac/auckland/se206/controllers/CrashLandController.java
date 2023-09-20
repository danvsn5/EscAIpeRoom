package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class CrashLandController {
  @FXML private Polygon forest;
  @FXML private ImageView forestImage;

  public void goForest() {
    App.setUi(AppPanel.OUTSIDE);
  }

  public void activateForestGlow() {
    forestImage.setEffect(GameState.glowBright);
  }

  public void deactivateForestGlow() {
    forestImage.setEffect(GameState.glowDim);
  }
}
