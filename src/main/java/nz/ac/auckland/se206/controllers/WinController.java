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

  public void iniVideos() throws Exception {

    mediaOne = new Media(App.class.getResource("/videos/launch/0000-0190.mp4").toURI().toString());
    mediaPlayerOne = new MediaPlayer(mediaOne);
    launchingVideo.setMediaPlayer(mediaPlayerOne);
    mediaPlayerOne.setCycleCount(1);

    mediaTwo = new Media(App.class.getResource("/videos/launch/0000-0480.mp4").toURI().toString());
    mediaPlayerTwo = new MediaPlayer(mediaTwo);
    flyingVideo.setMediaPlayer(mediaPlayerTwo);
    mediaPlayerTwo.setCycleCount(MediaPlayer.INDEFINITE);
    mediaPlayerOne.setOnEndOfMedia(
        new Runnable() {
          @Override
          public void run() {
            launchingVideo.setVisible(false);
            mediaPlayerTwo.play();
          }
        });

    // mediaPlayerTwo.setOnEndOfMedia(
    //     new Runnable() {
    //       @Override
    //       public void run() {
    //         launchingVideo.setVisible(false);
    //         mediaPlayerOne.play();
    //       }
    //     });
    // mediaPlayerOne.play();

  }

  public static void playMedia() {
    mediaPlayerOne.play();
  }
}
