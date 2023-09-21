package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class LoseController {

  @FXML private Button returnMenuButton;

  // upon losing, gamestate is reset and returns to launch menu when return button is pressed
  public void returnMenu() throws IOException {
    GameState.inventory.clear();
    App.setUi(AppPanel.LAUNCH);
  }
}
