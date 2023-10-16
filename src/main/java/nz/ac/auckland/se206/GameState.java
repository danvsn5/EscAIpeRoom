package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.controllers.ChatController;
import nz.ac.auckland.se206.controllers.ThrusterController;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** Represents the state of the game. */
public class GameState {

  // count variable that will be used as in-game counter
  public static int difficulty = 0;
  public static int timer = 0;
  public static TextToSpeech textToSpeech = new TextToSpeech();
  public static boolean textToSpeechSetting = false;
  public static boolean isGreetingShown;
  public static boolean firstRiddleSolved; // tracks if the first riddle has been solved.
  public static boolean secondRiddleSolved; // tracks if the second riddle has been solved.
  public static boolean isFirstMissionCompleted; // tracks if the first mission has been completed.

  // public static boolean isSecondGuideShown; // tracks if the second guide has been shown.

  public static int hintNumer = 1000;
  public static int currentHint = 0;
  public static int passWord = -1;
  public static int firstDigit;
  public static int secondDigit;

  public static boolean isSecondGuideShown;
  public static boolean isSecondMissionCompleted; // tracks if the first mission has been completed.

  public static boolean isBucketCollected = false;
  public static boolean isSandCollected = false;

  public static boolean isPuzzleShowed = false;

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
  // 8: fuel collected
  // once the control panel and the window is fixed ie. 6 and 7 are in the inventory, the game is
  // won; click on the control panel to finish the game
  //
  public static ArrayList<Integer> inventory = new ArrayList<Integer>();

  public static int randomColorNumber;

  // create random number between 1 and 4
  public static void createRandomColorNumber() {
    randomColorNumber = (int) (Math.random() * 4 + 1);
  }

  public static MissionManager missionManager = new MissionManager();
  public static ArrayList<Integer> missionList = new ArrayList<Integer>();
  public static ArrayList<Integer> missionListA = new ArrayList<Integer>();
  public static ProgressBars progressBarGroup = new ProgressBars();

  public static Glow glowBright = new Glow(0.5);
  public static Glow glowDim = new Glow(0.0);

  public static void setTimer(int timer) {
    // Set the timer to the correct time
    GameState.timer = timer;
  }

  public static int getTimer() {
    // Get the current time
    return GameState.timer;
  }

  public static void setDifficulty(int difficulty) {
    // Set the difficulty
    GameState.difficulty = difficulty;
  }

  public static int getDifficulty() {
    // Get the difficulty
    return GameState.difficulty;
  }

  public static int getRandomColorNumber() {
    // Get the number of the random color
    return randomColorNumber;
  }

  public static void setHintNumber(int max) {
    // Set the maximum hint number
    hintNumer = max;
  }

  public static void useHint() {
    // Use a hint
    // Increase the hint count
    currentHint++;
    if (difficulty == 1) {
      // If the difficulty is medium, show the remaining hint to label
      int hintRemain = hintNumer - currentHint;
      ((Label) SceneManager.getPanel(AppPanel.CHAT).lookup("#hintNumber"))
          .setText(Integer.toString(hintRemain));
    }
  }

  public static boolean hintUsedUp() {
    // Check if the hint used up
    return currentHint >= hintNumer;
  }

  public static void clearHint() {
    // Reset the used hint
    currentHint = 0;
  }

  public static void reset() {
    // Reset the game
    // Clear mission related variables
    MissionManager.missionList.clear();
    MissionManager.keyList.clear();
    missionList.clear();
    isFirstMissionCompleted = false;
    isSecondMissionCompleted = false;
    // Clear the inventory
    inventory.clear();
    isBucketCollected = false;
    isSandCollected = false;
    // Clear gpt related variables
    isGreetingShown = false;
    firstRiddleSolved = false;
    secondRiddleSolved = false;
    ChatController.seenFirstMessage = 0;
    // Reset thruster mission
    ThrusterController.buttonActivationCounter = 0;
    ThrusterController.isGameActive = 0;
    // Reset controller mission
    passWord = -1;
    // Reset root
    RootBorder.rootState = 1;
  }

  public static void generatePassWord() {
    Random rand = new Random();
    firstDigit = rand.nextInt(500);
    System.out.println(firstDigit);
    secondDigit = rand.nextInt(500);
    System.out.println(secondDigit);
    passWord = firstDigit + secondDigit;
    System.out.println(passWord);
  }

  public static void speak(String message) {
    if (textToSpeechSetting) {
      Thread speakThread =
          new Thread(
              () -> {
                textToSpeech.speak(message);
              });
      speakThread.start();
    }
  }
}
