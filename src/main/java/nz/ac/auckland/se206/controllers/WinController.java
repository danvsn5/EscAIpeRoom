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
  public static MediaPlayer mediaPlayerOne;
  @FXML private MediaView launchingVideo;
  private Media mediaTwo;
  public static MediaPlayer mediaPlayerTwo;

  public void initialize() throws Exception {
    iniVideos();
  }

  public void returnMenu() throws IOException {
    GameState.reset();
    App.setUi(AppPanel.LAUNCH);
  }

  public void quitGame() {
    Platform.exit();
    System.exit(0);
  }

  /**
   * Initializes the videos used in the win screen.
   * @throws Exception if there is an error loading the video files
   */
  public void iniVideos() throws Exception {

    mediaOne = new Media(App.class.getResource("/videos/launch/0000-0190.mp4").toURI().toString());
    mediaPlayerOne = new MediaPlayer(mediaOne);
    launchingVideo.setMediaPlayer(mediaPlayerOne); // sets the launching video to the media player one
    mediaPlayerOne.setCycleCount(1); // sets the video to play once

    mediaTwo = new Media(App.class.getResource("/videos/launch/0000-0480.mp4").toURI().toString());
    mediaPlayerTwo = new MediaPlayer(mediaTwo);
    flyingVideo.setMediaPlayer(mediaPlayerTwo); // sets the flying video to the media player two
    mediaPlayerTwo.setCycleCount(MediaPlayer.INDEFINITE); // sets the video to play indefinitely
    mediaPlayerOne.setOnEndOfMedia(
        new Runnable() { // when the first video ends, the second video will play
          @Override
          public void run() {
            launchingVideo.setVisible(false);
            mediaPlayerTwo.play();
          }
        });
  }

  public static void playMedia() {
    mediaPlayerOne.play();
  }
}
