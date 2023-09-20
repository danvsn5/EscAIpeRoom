package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class StorageController {

  @FXML private Rectangle storageDoor;
  @FXML private ImageView storageDoorImage;

  public void goInside() {
    App.setUi(AppPanel.MAIN_ROOM);
  }

  public void activateDoorGlow() {
    storageDoorImage.setEffect(GameState.glowBright);
  }

  public void deactivateDoorGlow() {
    storageDoorImage.setEffect(GameState.glowDim);
  }
}
