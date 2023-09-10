package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.SceneManager.AppPanel;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

public class WinController {

  @FXML private Button returnMenuButton;

  public void returnMenu() throws IOException {
    GameState.inventory.clear();
    App.setUi(AppPanel.LAUNCH);
  }
}
