package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class WinController {

  @FXML private Button returnMenuButton;
  @FXML private Button quitButton;
  @FXML private MediaView flyingVideo;
  private Media mediaOne;
  private MediaPlayer mediaPlayerOne;
  @FXML private MediaView launchingVideo;
  private Media mediaTwo;
  public static MediaPlayer mediaPlayerTwo;

  public void initialize() throws Exception {

    Task<Void> timelineTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            Timeline videoBuffer = new Timeline(new KeyFrame(Duration.millis(1000)));
            videoBuffer.setCycleCount(1);
            videoBuffer.setOnFinished(
                event -> {
                  iniVideos();
                });
            videoBuffer.play();
            return null;
          }
        };

    Thread timelineThread = new Thread(timelineTask);
    timelineThread.start();
  }

  public void returnMenu() throws IOException {
    GameState.inventory.clear();
    App.setUi(AppPanel.LAUNCH);
  }

  public void quitGame() {
    Platform.exit();
    System.exit(0);
  }

  public void iniVideos() {
    Task<Void> videoTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            mediaTwo =
                new Media(App.class.getResource("/videos/launch/launchWin.mp4").toURI().toString());
            mediaPlayerTwo = new MediaPlayer(mediaTwo);
            launchingVideo.setMediaPlayer(mediaPlayerTwo);
            mediaPlayerTwo.setCycleCount(1);

            mediaOne =
                new Media(App.class.getResource("/videos/launch/flyShip.mp4").toURI().toString());
            mediaPlayerOne = new MediaPlayer(mediaOne);
            flyingVideo.setMediaPlayer(mediaPlayerOne);
            mediaPlayerOne.setCycleCount(MediaPlayer.INDEFINITE);

            mediaPlayerTwo.setOnEndOfMedia(
                new Runnable() {
                  @Override
                  public void run() {
                    launchingVideo.setVisible(false);
                    mediaPlayerOne.play();
                  }
                });
            // mediaPlayerOne.play();

            return null;
          }
        };

    Thread videoInitialisationThread = new Thread(videoTask);
    videoInitialisationThread.start();
  }
}
