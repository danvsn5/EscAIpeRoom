package nz.ac.auckland.se206;

import java.util.ArrayList;
import javafx.scene.effect.Glow;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** Represents the state of the game. */
public class GameState {

  // count variable that will be used as in-game counter
  public static int difficulty = 0;
  public static int timer = 0;
  public static TextToSpeech textToSpeech = new TextToSpeech();
  public static boolean isGreetingShown;
  public static boolean firstRiddleSolved; // tracks if the first riddle has been solved.
  public static boolean secondRiddleSolved; // tracks if the second riddle has been solved.

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

  private static int randomColorNumber;

  // create random number between 1 and 4

  public static void createRandomColorNumber() {
    randomColorNumber = (int) (Math.random() * 4 + 1);
  }

  public static MissionManager missionManager = new MissionManager();
  public static ArrayList<Integer> missionList = new ArrayList<Integer>();
  public static ProgressBars progressBarGroup = new ProgressBars();

  public static Glow glowBright = new Glow(0.5);
  public static Glow glowDim = new Glow(0.0);

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

  public static int getRandomColorNumber() {
    return randomColorNumber;
  }
}
