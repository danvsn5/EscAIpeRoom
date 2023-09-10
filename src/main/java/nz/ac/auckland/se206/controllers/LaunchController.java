package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import nz.ac.SceneManager;
import nz.ac.SceneManager.AppPanel;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

public class LaunchController {

  private static Parent loadFxml(final String fxml) throws IOException {
    return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }

  @FXML private Button launchButton;
  @FXML private Button activeSpeech;
  @FXML private Button inactiveSpeech;

  // clears all instances of existing rooms, wipes out the inventory and resets the timeline
  public void launchGame(MouseEvent ev) throws IOException {
    SceneManager.clearMap();
    SceneManager.addPanel(AppPanel.MAIN_ROOM, loadFxml("mainRoom"));
    SceneManager.addPanel(AppPanel.OUTSIDE, loadFxml("outsideRoom"));
    SceneManager.addPanel(AppPanel.WORK, loadFxml("workRoom"));
    SceneManager.addPanel(AppPanel.LOSE, loadFxml("loseRoom"));
    SceneManager.addPanel(AppPanel.CHAT, loadFxml("chat"));
    SceneManager.addPanel(AppPanel.WIN, loadFxml("winRoom"));
    SceneManager.addPanel(AppPanel.LAUNCH, loadFxml("startRoom"));
    GameState.count = 120;
    GameState.timeline.setCycleCount(121);
    GameState.timeline.setOnFinished(event -> App.setUi(AppPanel.LOSE));
    GameState.timeline.playFromStart();
    App.setUi(AppPanel.MAIN_ROOM);
  }

  // determines whether or not text to speech will be used in the game
  public void activateSpeech() {
    GameState.inventory.add(-2);
  }

  public void removeSpeech() {
    GameState.inventory.clear();
  }
}
