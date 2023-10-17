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

  public static void playMedia() {
    mediaPlayerOne.play();
  }

  private Media mediaOne;
  private Media mediaTwo;

  @FXML private Button returnMenuButton;
  @FXML private Button quitButton;
  @FXML private MediaView flyingVideo;
  @FXML private MediaView launchingVideo;

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
   * Initializes the two videos used in the game. The first video is played once 
   * and the second video is played indefinitely.
   * The first video is set to the launching video and the second video 
   * is set to the flying video.
   * When the first video ends, the second video will play.
   *
   * @throws Exception if there is an error initializing the videos
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
}
