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

/**
 * The WinController class is responsible for controlling the win screen of the game.
 * It initializes the videos used in the win screen and provides methods for returning 
 * to the main menu and quitting the game.
 */
public class WinController {

  public static MediaPlayer mediaPlayerOne;
  public static MediaPlayer mediaPlayerTwo;

  @FXML private Button returnMenuButton;
  @FXML private Button quitButton;
  @FXML private MediaView flyingVideo;
  @FXML private MediaView launchingVideo;

  private Media mediaOne;
  private Media mediaTwo;

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
   * 
   * @throws Exception if there is an error loading the video files
   */
  public void iniVideos() throws Exception {

    mediaOne = new Media(App.class.getResource("/videos/launch/0000-0190.mp4").toURI().toString());
    mediaPlayerOne = new MediaPlayer(mediaOne);
    // sets the launching video to the media player one
    launchingVideo.setMediaPlayer(mediaPlayerOne);
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
