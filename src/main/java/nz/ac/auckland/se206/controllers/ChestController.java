package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class ChestController {
  @FXML private Button backButton;

  public void goBack() {
    App.setUi(AppPanel.MAIN_ROOM);
  }
}
