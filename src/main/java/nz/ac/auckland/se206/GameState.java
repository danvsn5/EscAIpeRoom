package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.controllers.ChatController;
import nz.ac.auckland.se206.controllers.OutsideController;
import nz.ac.auckland.se206.controllers.ThrusterController;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** Represents the state of the game. */
public class GameState {

  // count variable that will be used as in-game counter
  public static int difficulty = 0;
  public static int timer = 0;
  public static TextToSpeech textToSpeech = new TextToSpeech();
  public static boolean textToSpeechSetting = false;

  // First mission related variables
  public static boolean isGreetingShown;
  public static boolean firstRiddleSolved; // tracks if the first riddle has been solved.
  public static boolean isFirstMissionCompleted; // tracks if the first mission has been completed.
  public static boolean isBucketCollected = false;
  public static boolean isSandCollected = false;

  // Second mission related variables
  public static boolean isSecondGuideShown;
  public static boolean secondRiddleSolved; // tracks if the second riddle has been solved.
  public static boolean isSecondMissionCompleted; // tracks if the first mission has been completed.
  public static int firstDigit;
  public static int secondDigit;
  public static int passWord = -1;
  public static boolean isControllerPuzzleShown = false;

  // Hint related setting
  public static int hintNumer = 1000;
  public static int currentHint = 0;

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

  public static MissionManager missionManager = new MissionManager();
  public static ArrayList<Integer> missionList = new ArrayList<Integer>();
  public static ArrayList<Integer> missionListA = new ArrayList<Integer>();
  public static ProgressBars progressBarGroup = new ProgressBars();

  public static Glow glowBright = new Glow(0.5);
  public static Glow glowDim = new Glow(0.0);

  // create random number between 1 and 4
  public static void createRandomColorNumber() {
    randomColorNumber = (int) (Math.random() * 4 + 1);
  }

  /**
   * This method is used to set the timer.
   *
   * @param timer the timer to be set.
   */
  public static void setTimer(int timer) {
    // Set the timer to the correct time
    GameState.timer = timer;
  }

  /**
   * This method is used to get the timer.
   *
   * @return the timer.
   */
  public static int getTimer() {
    // Get the current time
    return GameState.timer;
  }

  /**
   * This method is used to set the difficulty.
   *
   * @param difficulty the difficulty to be set.
   */
  public static void setDifficulty(int difficulty) {
    // Set the difficulty
    GameState.difficulty = difficulty;
  }

  /**
   * This method is used to get the difficulty.
   *
   * @return the difficulty.
   */
  public static int getDifficulty() {
    // Get the difficulty
    return GameState.difficulty;
  }

  /**
   * Returns the number of the random color.
   *
   * @return the number of the random color.
   */
  public static int getRandomColorNumber() {
    // Get the number of the random color
    return randomColorNumber;
  }

  /**
  * Sets the maximum hint number.
  *
  * @param max the maximum hint number to be set.
  */
  public static void setHintNumber(int max) {
    // Set the maximum hint number
    hintNumer = max;
  }

  /** This method is used to increase the hint count. */
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

  /**
  * Checks if the hint has been used up.
  *
  * @return true if the hint has been used up, false otherwise.
  */
  public static boolean hintUsedUp() {
    // Check if the hint used up
    return currentHint >= hintNumer;
  }

  /**
   * Resets the current hint to 0.
   */
  public static void clearHint() {
    // Reset the used hint
    currentHint = 0;
  }

  /**
   * This method is used to reset the game.
   *
   * <p>It will reset all the variables and clear the inventory.
   */
  public static void reset() {
    // Reset the game
    // Clear mission related variables
    MissionManager.missionList.clear();
    MissionManager.keyList.clear();
    missionList.clear();

    // Clear the first mission variables
    ChatController.seenFirstMessage = 0;
    isGreetingShown = false;
    firstRiddleSolved = false;
    isFirstMissionCompleted = false;
    isBucketCollected = false;
    isSandCollected = false;

    // Clear the second mission variables
    isSecondGuideShown = false;
    secondRiddleSolved = false;
    isSecondMissionCompleted = false;
    passWord = -1;
    isControllerPuzzleShown = false;
    OutsideController.thrusterPuzzleGenerate = 0;
    ThrusterController.buttonActivationCounter = 0;
    ThrusterController.isGameActive = 0;

    // Clear the inventory
    inventory.clear();

    // Clear game setting
    hintNumer = 0;
    hintNumer = 1000;
    difficulty = 0;
    timer = 0;
    textToSpeechSetting = false;
    SceneManager.getPanel(AppPanel.LAUNCH).lookup("#launchButton").setDisable(false);
    SceneManager.getPanel(AppPanel.LAUNCH).lookup("#diffButton").setDisable(false);
    SceneManager.getPanel(AppPanel.LAUNCH).lookup("#timerButton").setDisable(false);
    SceneManager.getPanel(AppPanel.LAUNCH).lookup("#speechButton").setDisable(false);

    // Reset root
    RootBorder.rootState = 1;
    TreeAvatar.treeState = 0;
  }

  /** This method is used to generate the password for the second mission. */
  public static void generatePassWord() {
    Random rand = new Random();
    firstDigit = rand.nextInt(500); // first digit between 0 and 500.
    System.out.println(firstDigit);
    secondDigit = rand.nextInt(500); // second digit between 0 and 500.
    System.out.println(secondDigit);
    passWord = firstDigit + secondDigit; // add the two digits together.
    System.out.println(passWord);
  }


  /**
   * Speaks the given message using text-to-speech 
   * if the text-to-speech setting is enabled.
   * 
   * @param message the message to be spoken
   */
  public static void speak(String message) { // text to speech method.
    if (textToSpeechSetting) { // if text to speech is enabled, speak the message.
      Thread speakThread =
          new Thread(
              () -> {
                textToSpeech.speak(message);
              });
      speakThread.start(); // start the thread.
    }
  }
}
