package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import nz.ac.SceneManager;
import nz.ac.SceneManager.AppPanel;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.TimeCounter;

public class LaunchController {

  private static Parent loadFxml(final String fxml) throws IOException {
    return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }

  @FXML private Button launchButton;
  @FXML private Button diffButton;
  @FXML private Button timerButton;
  @FXML private Button speechButton;
  @FXML private Button quitButton;

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
    SceneManager.addPanel(AppPanel.PROGRESS, loadFxml("progressBars"));

    GameState.count = 120;
    GameState.timeline.setCycleCount(121);
    GameState.timeline.setOnFinished(event -> App.setUi(AppPanel.LOSE));
    GameState.timeline.playFromStart();
    createTimer();
    Random rand = new Random();
    int task1 = rand.nextInt(4) + 1;
    int task2 = rand.nextInt(4) + 1;
    while (task2 == task1) {
      task2 = rand.nextInt(4);
    }
    GameState.missionManager.addMission(task1);
    GameState.missionManager.addMission(task2);
    GameState.progressBarGroup.setMissionOne(task1);
    GameState.progressBarGroup.setMissionTwo(task2);

    App.setUi(AppPanel.MAIN_ROOM);
  }

  public void changeDiff() {
    // switch case for difficulty in Gamestate class for numbers between 0-2
    int difficulty = GameState.getDifficulty();

    switch (difficulty) {
      case 0:
        GameState.setDifficulty(1);
        diffButton.setText("Difficulty: Medium");
        break;
      case 1:
        GameState.setDifficulty(2);
        diffButton.setText("Difficulty: Hard");
        break;
      case 2:
        GameState.setDifficulty(0);
        diffButton.setText("Difficulty: Easy");
        break;
    }
  }

  public void changeTimer() {

    int timer = GameState.getTimer();

    switch (timer) {
      case 0:
        GameState.setTimer(1);
        timerButton.setText("Timer: Four Minutes");
        break;
      case 1:
        GameState.setTimer(2);
        timerButton.setText("Timer: Six Minutes");
        break;
      case 2:
        GameState.setTimer(0);
        timerButton.setText("Timer: Two Minutes");
        break;
    }
  }

  public void changeSpeech() {
    if (GameState.inventory.contains(-2)) {
      GameState.inventory.clear();
      speechButton.setText("Text to Speech: Off");
    } else {
      GameState.inventory.add(-2);
      speechButton.setText("Text to Speech: On");
    }
  }

  public void quitGame() {
    Platform.exit();
    System.exit(0);
  }

  public void createTimer() {
    Task<Void> timerTask =
        new Task<Void>() {
          @Override
          protected Void call() {
            TimeCounter timer;
            if (GameState.timer == 0) {
              timer = new TimeCounter(2, 0);
            } else if (GameState.timer == 1) {
              timer = new TimeCounter(4, 0);
            } else {
              timer = new TimeCounter(6, 0);
            }
            while (true) {
              if (timer.isEnd()) {
                return null;
              }

              // Every 1s, update the clock
              if (!timer.isEnd()) {
                Platform.runLater(
                    () -> {
                      System.out.println(timer.getTime());
                      updateClock(timer.getTime());
                    });
              }

              // Implement methods for 25%, 50%, 75% progress of game
              if (timer.getRemainingPercentage() == 75) {
                System.out.println("25% passed");
              } else if (timer.getRemainingPercentage() == 50) {
                System.out.println("50% passed");
              } else if (timer.getRemainingPercentage() == 25) {
                System.out.println("75% passed");
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
    Thread timerThread = new Thread(timerTask);
    timerThread.start();
  }

  public void updateClock(String time) {
    ((Label) SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#counter")).setText(time);
  }
}
