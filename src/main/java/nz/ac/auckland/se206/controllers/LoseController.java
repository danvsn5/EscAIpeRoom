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

public class LoseController {

  @FXML private Button returnMenuButton;
  @FXML private Button quitButton;
  @FXML private MediaView loopingEnd;
  private Media mediaOne;
  public static MediaPlayer mediaPlayerOne;

  public void initialize() throws Exception {
    // iniVideo();
  }

  // upon losing, gamestate is reset and returns to launch menu when return button is pressed
  public void returnMenu() throws IOException {
    GameState.reset();
    App.setUi(AppPanel.LAUNCH);
  }

  public void quitGame() {
    Platform.exit();
    System.exit(0);
  }

  public void iniVideo() throws Exception {

    mediaOne = new Media(App.class.getResource("/videos/launch/loopingEnd.mp4").toURI().toString());
    mediaPlayerOne = new MediaPlayer(mediaOne);
    loopingEnd.setMediaPlayer(mediaPlayerOne);
    mediaPlayerOne.setCycleCount(MediaPlayer.INDEFINITE);
  }

  public static void playMedia() {
    mediaPlayerOne.play();
  }
}
