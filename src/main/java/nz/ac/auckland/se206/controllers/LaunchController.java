package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import nz.ac.SceneManager;
import nz.ac.SceneManager.AppPanel;
import nz.ac.auckland.RootBorder;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

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
    GameState.count = 120;
    GameState.timeline.setCycleCount(121);
    GameState.timeline.setOnFinished(event -> App.setUi(AppPanel.LOSE));
    GameState.timeline.playFromStart();

    int timerState = GameState.getTimer();

    switch (timerState) {
      case 0:
        RootBorder.treeTimelineTwo.setCycleCount(3);
        RootBorder.treeTimelineTwo.play();
        break;
      case 1:
        RootBorder.treeTimelineFour.setCycleCount(3);
        RootBorder.treeTimelineFour.play();
        break;
      case 2:
        RootBorder.treeTimelineSix.setCycleCount(3);
        RootBorder.treeTimelineSix.play();
        break;
    }
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
}
