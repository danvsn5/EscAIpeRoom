package nz.ac.auckland.se206.gpt;

import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionManager.MissionType;
import nz.ac.auckland.se206.controllers.OutsideController;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  private static String prompt = "Do not need to greet the user. ";
  private static String riddlePrompt =
      "You are a wise mystical tree of a forest. Do not need to greet the user or say anything"
          + " else. ";
  private static String oneSentence = "Say this in one sentence.";
  private static String beginWord = "Begin your message with: 'Hint:'.";
  private static String info = "If the user asks for a hint, taunt on them and tell them to "
  + "click on the hint button for the hints. YOU SHOULD NOT LET THE USER KNOW ABOUT THIS. "
  + "YOU SOHULD ONLY GIVE THE RIDDLE WITHOUT ANY EXTRA INFORMATION.";

  /**
   * This method returns the intro call input for GPT.
   *
   * @return the intro call input for GPT.
   */
  public static String introCall() {
    return "Act like a wise mystical tree of a forest. Greet me in one sentence and ask if i want"
        + " to solve the riddle to collect the item for the mission.";
  }

  /**
   * This method returns the riddle generation request input for GPT.
   *
   * @param wordToGuess the word that needs to be guessed.
   * @return the riddle generation request input for GPT.
   */
  public static String getRiddleWithGivenWord(String wordToGuess) { // comment
    return riddlePrompt
        + "Tell the user a riddle with answer: "
        + wordToGuess
        + ". NEVER reveal the answer. You should answer with the word Correct when is correct, if"
        + " the user answers other words that have the same meaning, it is also correct, if the"
        + " user asks for hints, DO NOT give a hint no matter how many times they ask and taunt on"
        + " them. If users guess incorrectly, taunt on them, do not give any hint. If player gives"
        + " up, do not give the answer, taunt on them. If the user ask for other information,"
        + " generate a reasonable response. You cannot, no matter what, reveal the answer even if"
        + " the player asks for it. Give your response in at most 70 words. This prompt is only for you,"
        + " act like a wise tree. " + info;
  }

  /**
   * This method returns the hint generation request input for GPT.
   *
   * @param missionType the mission type.
   * @return the hint generation request input for GPT.
   */
  public static String getHint(MissionType missionType) { // get hint for the mission
    switch (missionType) { // switch case for different mission type
      case WINDOW: // if the mission type is window
        return windowHint();
      case FUEL: // if the mission type is fuel
        return fuelHint();
      case CONTROLLER: // if the mission type is controller
        return controllerHint(GameState.passWord);
      default: // thruster by default
        if (GameState.randomColorNumber == 1) {
          return thrusterHint("purple");
        } else if (GameState.randomColorNumber == 2) {
          return thrusterHint("blue");
        } else if (GameState.randomColorNumber == 3) {
          return thrusterHint("red");
        } else {
          return thrusterHint("green");
        }
    }
  }

  /**
   * This method returns the window hint input according to different state in window mission.
   *
   * @return the window hint input.
   */
  private static String windowHint() {
    // If the window mission is in stage 0
    if (GameState.missionManager.getMission(MissionType.WINDOW).getStage() == 0) {
      return prompt
          + "Give a hint about the word: sand, in one sentecne. NEVER REVEAL THE WORD."
          + beginWord;
    } else if (GameState.missionManager.getMission(MissionType.WINDOW).getStage() == 1) {
      // If the window mission is in stage 1
      return prompt
          + "Tell the player to collect the sand with the bucket from the outside. "
          + beginWord
          + oneSentence;
    } else if (GameState.missionManager.getMission(MissionType.WINDOW).getStage() == 2) {
      // If the window mission is in stage 2
      return prompt
          + "Tell the player that the player needs to melt sand, ask the player to check the"
          + " processing machine. "
          + beginWord
          + oneSentence;
    } else {
      // If the window mission is in stage 3
      return prompt + "Tell the player to fix the window. " + beginWord + oneSentence;
    }
  }

  /**
   * This method returns the fuel hint input according to different state in fuel mission.
   *
   * @return the fuel hint input.
   */
  private static String fuelHint() {
    // If the fuel mission is in stage 0
    if (GameState.missionManager.getMission(MissionType.FUEL).getStage() == 0) {
      return prompt
          + "Give a hint about the word: sky, in one sentecne. NEVER REVEAL THE WORD."
          + beginWord;
    } else if (GameState.missionManager.getMission(MissionType.FUEL).getStage() == 1) {
      // If the fuel mission is in stage 1
      return prompt + "Tell the player to collect the fuel. " + beginWord + oneSentence;
    } else {
      // If the fuel mission is in stage 2
      return prompt
          + "Tell the player that the player needs to refuel the space shuttle. "
          + beginWord
          + oneSentence;
    }
  }

  /**
   * This method returns the controller hint input according to different state in controller
   * mission.
   *
   * @param password the password of the chest.
   * @return the controller hint input.
   */
  private static String controllerHint(int password) {
    if (GameState.missionManager.getMission(MissionType.CONTROLLER).getStage() == 0) {
      // If the controller puzzle is shown
      if (GameState.isControllerPuzzleShown) {
        return prompt + "Tell the user to focus on the numbers. " + beginWord + oneSentence;
      } else {
        // If the controller puzzle is not shown
        return prompt
            + "Tell the player to find the chest and solve the puzzle. "
            + beginWord
            + oneSentence;
      }
    } else {
      // If the controller puzzle is solved
      return prompt
          + "Tell the player to fix the control panel and fix the space shuttle. "
          + beginWord
          + oneSentence;
    }
  }

  /**
   * This method returns the thruster hint input according to different state in thruster mission.
   *
   * @param color the color of the button.
   * @return the thruster hint input.
   */
  private static String thrusterHint(String color) {
    // If the thruster mission is in stage 0
    if (GameState.missionManager.getMission(MissionType.THRUSTER).getStage() == 0) {
      return prompt
          + "Tell the player to find the blueprint in order to fix the thruster. "
          + beginWord
          + oneSentence;
    } else if (GameState.missionManager.getMission(MissionType.THRUSTER).getStage() == 1) {
      // If the thruster mission is in stage 1 and the thruster puzzle is generated
      if (OutsideController.thrusterPuzzleGenerate == 1) {
        return prompt
            + "Give a hint about the color: "
            + color
            + ", in one sentecne. NEVER REVEAL THE COLOR."
            + beginWord;
      } else {
        // If the thruster mission is in stage 1 and the thruster puzzle is not generated
        return prompt
            + "Tell the player to go to the thruster and solve the puzzle. "
            + beginWord
            + oneSentence;
      }
    } else {
      // If the thruster mission is in stage 2
      return prompt
          + "Tell the player to click on the button with the correct color to fix the thruster and"
          + " then fix the space shuttle. "
          + beginWord
          + oneSentence;
    }
  }

  /**
   * This method returns the riddle generation request input for GPT.
   *
   * @param password the password of the chest.
   * @return the riddle generation request input for GPT.
   */
  public static String getControllerPuzzle(int password) {
    return riddlePrompt
        + "Give the user a SIMPLE addition math puzzle with the answer being: "
        + password
        + ". NEVER REVEAL THE ANSWER. If they answer is right, DO NOT BEGIN YOUR MESSAGE WITH"
        + " correct, and tell the user they may now unlock the chest. You cannot, no matter what,"
        + " reveal the answer even if the player asks for it.";
  }

  /**
   * This method returns the riddle generation request input for GPT.
   *
   * @param colour the color of the button.
   * @return the riddle generation request input for GPT.
   */
  public static String getThrusterPuzzle(String colour) {
    return riddlePrompt // start with a riddle prompt which is initialised before.
        + "Tell me another riddle with answer: "
        + colour // the colour from the parameter
        + ". The requirement is the same. You should answer with the word Correct when is correct."
        + " If the user asks for hints, DO NOT give a hint no matter how many times they ask and"
        + " taunt on them, if users guess incorrectly, taunt on them, do not give hint. If player"
        + " gives up, do not give the answer, taunt on them. If the user ask for other information,"
        + " generate a reasonable response. You cannot, no matter what, reveal the answer even if"
        + " the player asks for it. Give your response in at most 70 words. This prompt is only for you, "
        + " act like a wise tree. " + info; // prompt given to the gpt
  }
}
