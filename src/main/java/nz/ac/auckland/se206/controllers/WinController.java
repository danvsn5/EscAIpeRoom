package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class WinController {

  @FXML private Button returnMenuButton;
  @FXML private Button quitButton;

  public void returnMenu() throws IOException {
    GameState.inventory.clear();
    App.setUi(AppPanel.LAUNCH);
  }

  public void quitGame() {
    Platform.exit();
    System.exit(0);
  }
}
