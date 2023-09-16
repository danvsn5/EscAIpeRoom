package nz.ac.auckland.se206;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** Represents the state of the game. */
public class GameState {

  // count variable that will be used as in-game counter
  public static int count = 120;
  public static int difficulty = 0;
  public static int timer = 0;
  public static TextToSpeech textToSpeech = new TextToSpeech();

  // inventory holds integers that correspond to different actions having taken place:
  // -1: riddle has been solved
  // 0: hammer
  // 1: tech
  // 2: sand
  // 3: glass
  // 4: window
  // 5: new control panel
  // 6: fixed window
  // 7: fixed control panel
  // once the control panel and the window is fixed ie. 6 and 7 are in the inventory, the game is
  // won; click on the control panel to finish the game
  //
  public static ArrayList<Integer> inventory = new ArrayList<Integer>();

  public static Timeline timeline =
      new Timeline(new KeyFrame(Duration.millis(1000), e -> changeCount()));

  public static void setTimer(int timer) {
    GameState.timer = timer;
  }

  public static int getTimer() {
    return GameState.timer;
  }

  public static void setDifficulty(int difficulty) {
    GameState.difficulty = difficulty;
  }

  public static int getDifficulty() {
    return GameState.difficulty;
  }

  public static void changeCount() {
    GameState.count--;
  }
}
