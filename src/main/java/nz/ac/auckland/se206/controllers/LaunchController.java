package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionInitialise;
import nz.ac.auckland.se206.RootBorder;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.TimeCounter;

public class LaunchController {

  private static Parent loadFxml(final String fxml) throws IOException {
    return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }

  public static TimeCounter timer;

  @FXML private Button launchButton;
  @FXML private Button diffButton;
  @FXML private Button timerButton;
  @FXML private Button speechButton;
  @FXML private Button quitButton;
  @FXML private MediaView loopingVideo;
  @FXML private MediaView launchVideo;
  private Media mediaOne;
  private MediaPlayer mediaPlayerOne;
  private Media mediaTwo;
  private MediaPlayer mediaPlayerTwo;

  private String mission1;
  private String mission2;

  private MissionInitialise missionInitialise = new MissionInitialise();

  /**
   * Initializes the launch panel by creating a timeline that runs for 200 milliseconds and start
   * the animation.
   *
   * @throws Exception if an exception occurs.
   */
  public void initialize() throws Exception {

    Task<Void> timelineTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            Timeline videoBuffer = new Timeline(new KeyFrame(Duration.millis(200)));
            videoBuffer.setCycleCount(1); // sets the Timeline to run once
            videoBuffer
                .setOnFinished( // once the Timeline is finished, it will run the following code
                    event -> {
                      initialiseVideo();
                    });
            videoBuffer.play(); // plays the Timeline
            return null;
          }
        };

    // Start the thread
    Thread timelineThread = new Thread(timelineTask);
    timelineThread.start(); // starts the thread
  }

  /**
   * Launches the game and initializes the necessary components such as media players, tasks, and
   * panels. Disables launchButton, diffButton, timerButton, and speechButton. Sets loopingVideo to
   * invisible and plays mediaPlayerTwo once mediaPlayerOne ends. Adds panels to SceneManager and
   * initializes missions and progress bars. Sets guideLabel text to a message containing the first
   * mission and a clue to find the mysterious tree. Sets previous panel to MAIN_ROOM.
   *
   * @param ev MouseEvent that triggers the launchGame method.
   * @throws IOException if an I/O error occurs.
   */
  public void launchGame(MouseEvent ev) throws IOException {
    // clears all instances of existing rooms, wipes out the inventory and resets the timeline
    launchButton.setDisable(true);
    diffButton.setDisable(true);
    timerButton.setDisable(true);
    speechButton.setDisable(true);

    // When the first video ends, the second video will play
    mediaPlayerOne.setOnEndOfMedia(
        new Runnable() {
          @Override
          public void run() {
            loopingVideo.setVisible(false);
            mediaPlayerTwo.setCycleCount(1);
            mediaPlayerTwo.play();
          }
        });

    // When the second video ends, the game will start
    mediaPlayerTwo.setOnEndOfMedia(
        new Runnable() {
          @Override
          public void run() {
            App.setUi(AppPanel.MAIN_ROOM);
          }
        });

    // clear all the rooms
    SceneManager.clearMap();

    // Initialise the game
    Task<Void> initialiseTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            // Choose missions
            Random rand = new Random();
            int task1 = rand.nextInt(2) + 1;
            int task2 = rand.nextInt(2) + 3;
            GameState.missionListA.add(task1);
            GameState.missionListA.add(task2);
            // Initialize rooms
            SceneManager.addPanel(AppPanel.MAIN_ROOM, loadFxml("mainRoom"));
            SceneManager.addPanel(AppPanel.OUTSIDE, loadFxml("outsideRoom"));
            SceneManager.addPanel(AppPanel.LOSE, loadFxml("loseRoom"));
            SceneManager.addPanel(AppPanel.WIN, loadFxml("winRoom"));
            SceneManager.addPanel(AppPanel.LAUNCH, loadFxml("startRoom"));
            SceneManager.addPanel(AppPanel.THRUSTER, loadFxml("thrusterRoom"));
            SceneManager.addPanel(AppPanel.CHEST, loadFxml("chest"));
            SceneManager.addPanel(AppPanel.PROGRESS, loadFxml("progressBars"));
            SceneManager.addPanel(AppPanel.STORAGE, loadFxml("storage"));
            SceneManager.addPanel(AppPanel.CHAT, loadFxml("chat"));
            // Create timer
            createTimer();
            // Choose random color
            GameState.createRandomColorNumber();
            // Add missions
            GameState.missionManager.addMission(task1);
            GameState.missionManager.addMission(task2);
            GameState.missionList.add(task1);
            GameState.missionList.add(task2);
            GameState.progressBarGroup.setMissionOne(task1);
            GameState.progressBarGroup.setMissionTwo(task2);
            missionInitialise.initialiseFirstMission(task1);
            missionInitialise.initialiseSecondMission(task2);

            // Set the guide label
            if (GameState.missionList.contains(1)) {
              mission1 = "repairing the broken window";
            } else if (GameState.missionList.contains(2)) {
              mission1 = "refueling the empty tank";
            }

            if (GameState.missionList.contains(3)) {
              mission2 = "replacing the broken control panel";
            } else if (GameState.missionList.contains(4)) {
              mission2 = "recalibrating the thrusters";
            }
            // Set the guide text
            String text =
                "Hmmmmm... Outsider... It appears you've crash-landed on MY planet. Wish to escape?"
                    + " \n"
                    + "You must ready your ship by first "
                    + mission1
                    + " and then "
                    + mission2
                    + ".\n"
                    + "But... I will not make your venture easy \n"
                    + "Find me outside should you wish to be challenged.";
            // Set the text to the guide label
            Platform.runLater(
                () -> {
                  ((Label) SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#guideLabel"))
                      .setText(text);
                });

            SceneManager.setPrevious(AppPanel.MAIN_ROOM);

            return null;
          }
        };
    // Start the thread
    Thread videoInitialisationThread = new Thread(initialiseTask);
    videoInitialisationThread.start();
  }

  /**
   * Changes the difficulty level of the game and updates the hint number and difficulty button text
   * accordingly. The difficulty level is determined by the current value of
   * GameState.getDifficulty(). If the current difficulty is 0, sets the difficulty to 1 (medium),
   * sets the hint number to 5, and updates the button text to "Difficulty: Medium". If the current
   * difficulty is 1, sets the difficulty to 2 (hard), sets the hint number to 0, and updates the
   * button text to "Difficulty: Hard". If the current difficulty is 2, sets the difficulty to 0
   * (easy), sets the hint number to 1000, and updates the button text to "Difficulty: Easy".
   */
  public void changeDiff() {
    // switch case for difficulty in Gamestate class for numbers between 0-2
    int difficulty = GameState.getDifficulty();

    switch (difficulty) {
      case 0:
        // Set the difficulty to medium and set the hint number to 5
        GameState.setDifficulty(1);
        GameState.setHintNumber(5);
        diffButton.setText("Difficulty: Medium");
        break;
      case 1:
        // Set the difficulty to hard and set the hint number to 0
        GameState.setDifficulty(2);
        GameState.setHintNumber(0);
        diffButton.setText("Difficulty: Hard");
        break;
      case 2:
        // Set the difficulty to easy and set the hint number to 1000
        GameState.setDifficulty(0);
        GameState.setHintNumber(1000);
        diffButton.setText("Difficulty: Easy");
        break;
    }
  }

  /** Changes the timer value and updates the text of the timer button accordingly. */
  public void changeTimer() {

    int timer = GameState.getTimer(); // gets the current timer

    switch (timer) {
      case 0: // if it is 0, set the timer to 1 and change the text of the button
        GameState.setTimer(1);
        timerButton.setText("Timer: Four Minutes");
        break;
      case 1: // if it is 1, set the timer to 2 and change the text of the button
        GameState.setTimer(2);
        timerButton.setText("Timer: Six Minutes");
        break;
      case 2: // if it is 2, set the timer to 0 and change the text of the button
        GameState.setTimer(0);
        timerButton.setText("Timer: Two Minutes");
        break;
    }
  }

  /** Change the text to speech setting. */
  public void changeSpeech() {
    if (GameState.textToSpeechSetting) {
      // Set tts to on and change the text of the button
      GameState.textToSpeechSetting = false;
      speechButton.setText("Text to Speech: Off");
    } else {
      // Set tts to off and change the text of the button
      GameState.textToSpeechSetting = true;
      speechButton.setText("Text to Speech: On");
    }
  }

  public void quitGame() {
    Platform.exit();
    System.exit(0);
  }

  /** Create a timer base on the setting and run it background. */
  public void createTimer() {
    Task<Void> timerTask =
        new Task<Void>() {
          @Override
          protected Void call() {
            // Create a timer base on the setting
            if (GameState.timer == 0) {
              timer = new TimeCounter(2, 0);
            } else if (GameState.timer == 1) {
              timer = new TimeCounter(4, 0);
            } else {
              timer = new TimeCounter(6, 0);
            }
            // Run the timer in background
            while (true) {
              if (timer.isEnd()) {
                return null;
              }

              // Every 1s, update the clock
              if (!timer.isEnd()) {
                Platform.runLater(
                    () -> {
                      updateClock(timer.getTime());
                    });
              }

              // Implement methods for 25%, 50%, 75% progress of game
              if (timer.getRemainingPercentage() == 75) {
                System.out.println("25% passed");
                // RootBorder.rootGrow();
                Platform.runLater(
                    () -> {
                      RootBorder.rootGrow();
                    });
              } else if (timer.getRemainingPercentage() == 50) {
                System.out.println("50% passed");
                Platform.runLater(
                    () -> {
                      RootBorder.rootGrow();
                    });

              } else if (timer.getRemainingPercentage() == 25) {
                System.out.println("75% passed");
                Platform.runLater(
                    () -> {
                      RootBorder.rootGrow();
                    });
              }

              // Decrease the counter by 1 unit every 1 second
              try {
                Thread.sleep(1000);
                timer.decrease();
              } catch (InterruptedException e) {
                System.out.println("Interrupted Exception in timer thread");
              }
            }
          }
        };
    // Start the timer thread
    Thread timerThread = new Thread(timerTask);
    timerThread.start();
  }

  /**
   * Update the clock in every room.
   *
   * @param time the time that needs to be updated.
   */
  public void updateClock(String time) {
    // update the clock in every room.
    // main room, outside room, and chat room.
    ((Label) SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#counter")).setText(time);
    ((Label) SceneManager.getPanel(AppPanel.CHAT).lookup("#counter")).setText(time);
    ((Label) SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#counter")).setText(time);
    // chest room, storage room, progress room, and thruster room.
    ((Label) SceneManager.getPanel(AppPanel.CHEST).lookup("#counter")).setText(time);
    ((Label) SceneManager.getPanel(AppPanel.STORAGE).lookup("#counter")).setText(time);
    ((Label) SceneManager.getPanel(AppPanel.PROGRESS).lookup("#counter")).setText(time);
    ((Label) SceneManager.getPanel(AppPanel.THRUSTER).lookup("#counter")).setText(time);
  }

  /**
   * Initializes the video by creating two media players and setting them to the looping and launch
   * videos respectively. The videos are played indefinitely and run on a separate thread.
   */
  public void initialiseVideo() {
    // initialise the video
    Task<Void> videoTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            // create media one
            mediaOne =
                new Media(App.class.getResource("/videos/launch/0001-0180.mp4").toURI().toString());
            mediaPlayerOne = new MediaPlayer(mediaOne);
            loopingVideo.setMediaPlayer(mediaPlayerOne); // sets the video to the media player one
            mediaPlayerOne.setCycleCount(MediaPlayer.INDEFINITE); // sets the video to loop
            mediaPlayerOne.play();

            // create media two
            mediaTwo =
                new Media(App.class.getResource("/videos/launch/0180-0330.mp4").toURI().toString());
            mediaPlayerTwo = new MediaPlayer(mediaTwo);
            launchVideo.setMediaPlayer(mediaPlayerTwo); // sets the video to the media player two

            return null;
          }
        };
    // Start the thread
    Thread videoInitialisationThread = new Thread(videoTask);
    videoInitialisationThread.start(); // starts the thread
  }
}
