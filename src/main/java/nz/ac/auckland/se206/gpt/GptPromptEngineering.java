package nz.ac.auckland.se206.gpt;

import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionManager.MISSION;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  // all calls will be done immediately with different thread so that when they need to be shown to
  // the screen by changing the labels of text, no time is wasted and the GUI does not freeze.
  public static String introCall() {
    return "Act like a wise mystical tree of a forest. Greet me in one sentence and ask if i want"
        + " to solve the riddle to collect the item for the mission.";
  }

  public static String loseCall() {
    return "";
  }

  public static String workTaunt() {
    return "";
  }

  public static String halfwayTaunt() {
    return "";
  }

  // public static String getRiddleWithGivenWordWindow(String wordToGuess) {
  //   return "You are a wise mystical tree of a forest. Tell the user a riddle with answer: "
  //       + wordToGuess
  //       + ". NEVER reveal the answer "
  //       + wordToGuess
  //       + "Do not change the answer to riddle, in any circumstance "
  //       + wordToGuess
  //       + " is the correct answer. The riddle itself should never contain the answer word
  // directly."
  //       + " NEVER reveal the answer. You should answer with the word 'Correct' if the player
  // guess"
  //       + " the answer. If the answer is incorrect, you should say it is incorrect. You can
  // NEVER"
  //       + " reveal the answer in any response or sentence, even if the player asks for it. Even
  // if"
  //       + " the player gives up or gets incorrect, do not give the answer. Do not give a hint,
  // if"
  //       + " the player ask for it, say no. The only sircumstance you can provide hint is the
  // player"
  //       + " says following word: 'SYSTEM.HINT'. When the user gusses correctly, tell them to go"
  //       + " back and collect the answer.";
  // }

  public static String getRiddleWithGivenWordWindow(String wordToGuess) { // comment
    return "You are a mean mystical tree of a forest. Tell the user a riddle with answer: "
        + wordToGuess
        + ". You should answer with the word Correct when is correct, if the user answers other"
        + " words that have the same meaning, it is also correct, if the user asks for hints, DO"
        + " NOT give a hint no matter how many times they ask and taunt on them, if users guess"
        + " incorrectly, taunt on them, do not give hint. If player gives up, do not give the"
        + " answer, taunt on them. If the user ask for other information, generate a reasonable"
        + " response. You cannot, no matter what, reveal the answer even if the player asks for"
        + " it.";
  }

  // public static String getRiddleWithGivenWordFuel(String wordToGuess, String wordToGuess2) {
  //   return "You are a wise mystical tree of a forest. Tell the user two riddles in a row. The"
  //       + " answer to the first riddle is: "
  //       + wordToGuess
  //       + ". NEVER reveal the answer. Do not say the answer when giving the riddle. Do not
  // change"
  //       + " the answer to riddle. In any circumstance "
  //       + wordToGuess
  //       + " is the correct answer. You should answer the phrase 'You are right' when is"
  //       + " correct and then give the second riddle with answer: "
  //       + wordToGuess2
  //       + ". You should answer with the word 'Correct' when is correct. Do not accept any other"
  //       + " answers as correct or change the answer to riddle. If the answer is incorrect, you"
  //       + " should say it is incorrect. You can NEVER reveal the answers in any response or"
  //       + " sentence, even if the player asks for it. Even if player gives up, do not give the"
  //       + " answer.";
  // }

  public static String getRiddleWithGivenWordFuel(
      String wordToGuess, String wordToGuess2) { // comment
    return "You are a mean mystical tree of a forest. Tell the user a riddle with answer: "
        + wordToGuess
        + ". You should answer with the word 'You are right' when is correct and give a second"
        + " riddle with answer: "
        + wordToGuess2
        + ", if the player answers correct in second riddle you should answer with the word Correct"
        + " when is correct, if the user answers other words that have the same meaning, it is also"
        + " correct, if the user asks for hints, DO NOT give a hint no matter how many times they"
        + " ask and taunt on them, if users guess incorrectly, taunt on them, do not give hint. If"
        + " player gives up, do not give the answer, taunt on them. If the user ask for other"
        + " information, generate a reasonable response. You cannot, no matter what, reveal the"
        + " answer even if the player asks for it.";
  }

  public static String getHint(MISSION missionType) { // get hint for the mission
    switch (missionType) { // switch case for different mission type
      case WINDOW: // window
        return windowHint();
      case FUEL: // fuel
        return fuelHint();
      case CONTROLLER: // controller
        return controllerHint();
      default: // thruster by default
        return thrusterHint();
    }
  }

  private static String windowHint() { // comment
    if (GameState.missionManager.getMission(MISSION.WINDOW).getStage() == 0) {
      return "Tell me in your word to think of tiny things, this is not a hint, do not give a hint"
          + " in future, it is still a part of riddle and you should answer word 'Correct'"
          + " if the player guessed the answer. Don't repeat my word, limit your response in"
          + " one sentence.";
    } else if (GameState.missionManager.getMission(MISSION.WINDOW).getStage() == 1) {
      return "Tell the player to collect the rewward";
    } else if (GameState.missionManager.getMission(MISSION.WINDOW).getStage() == 2) {
      return "Tell the player that the player needs to melt sand, ask the player to check the"
          + " furnace";
    } else {
      return "Tell the player to fix the window";
    }
  }

  private static String fuelHint() { // comment
    if (GameState.missionManager.getMission(MISSION.FUEL).getStage() == 0) {
      return "Tell me in your word to think of the nature or huge stuff, this is not a hint, do not"
          + " give a hint in future, it is still a part of riddle and you should answer word"
          + " 'Correct' if the player guessed the answer. Don't repeat my word, limit your"
          + " response in one sentence.";
    } else if (GameState.missionManager.getMission(MISSION.FUEL).getStage() == 1) {
      return "Tell the player to collect the fuel";
    } else {
      return "Tell the player that the player needs to refuel the space shuttle";
    }
  }

  private static String controllerHint() {
    if (GameState.missionManager.getMission(MISSION.CONTROLLER).getStage() == 0) {
      return "Tell the player to find a chest, and focus on numbers";
    } else {
      return "Tell the player to fix the controller in the bridge";
    }
  }

  private static String thrusterHint() {
    if (GameState.missionManager.getMission(MISSION.THRUSTER).getStage() == 0) {
      return "Tell the player to find the blueprint in order to fix the thruster";
    } else if (GameState.missionManager.getMission(MISSION.THRUSTER).getStage() == 1) {
      return "Give the player a hint about the riddle";
    } else {
      return "Tell the player to click on the button with correct color";
    }
  }

  public static String getGuideToSecondMission(String mission) {
    return "Act like a wise mystical tree of a forest. Tell the user to move on to next mission."
        + " Next mission is: "
        + mission
        + ". ";
  }

  public static String getControllerPuzzle(int password) {
    return "Give me a simple addition or subtraction problem where the answer MUST be"
        + password
        + ". NEVER reveal the"
        + " answer.You should answer with the word 'Correct' when is correct. If the answer"
        + " is incorrect, you should say it is incorrect. ou can NEVER reveal the answers in"
        + " any response or sentence, even if the player asks for it. Even if player gives"
        + " up, do not give the answer.";
  }

  public static String getThrusterPuzzle(String colour) {
    return "Tell the user a riddle with answer: "
        + colour
        + ". You should answer with the word Correct when is correct, if the user answers other"
        + " words that have the same meaning, it is also correct, if the user asks for hints, DO"
        + " NOT give a hint no matter how many times they ask and taunt on them, if users guess"
        + " incorrectly, taunt on them, do not give hint. If player gives up, do not give the"
        + " answer, taunt on them. If the user ask for other information, generate a reasonable"
        + " response. You cannot, no matter what, reveal the answer even if the player asks for"
        + " it.";
  }
}
