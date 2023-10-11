package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class WinController {

  @FXML private Button returnMenuButton;
  @FXML private Button quitButton;
  @FXML private MediaView flyingVideo;
  private Media mediaOne;
  private MediaPlayer mediaPlayerOne;

  public void initialize() throws Exception {
    mediaOne = new Media(App.class.getResource("/videos/launch/flyShip.mp4").toURI().toString());
    mediaPlayerOne = new MediaPlayer(mediaOne);
    flyingVideo.setMediaPlayer(mediaPlayerOne);
    mediaPlayerOne.setCycleCount(MediaPlayer.INDEFINITE);
    mediaPlayerOne.play();
  }

  public void returnMenu() throws IOException {
    GameState.inventory.clear();
    App.setUi(AppPanel.LAUNCH);
  }

  public void quitGame() {
    Platform.exit();
    System.exit(0);
  }
}
