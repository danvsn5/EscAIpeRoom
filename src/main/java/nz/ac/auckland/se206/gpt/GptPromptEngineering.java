package nz.ac.auckland.se206.gpt;

import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionManager.MISSION;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  private static String prompt = "You are a mean wise mystical tree of a forest. Do not need to greet the user. ";

  // all calls will be done immediately with different thread so that when they need to be shown to
  // the screen by changing the labels of text, no time is wasted and the GUI does not freeze.
  public static String introCall() {
    return "Act like a wise mystical tree of a forest. Greet me in one sentence and ask if i want"
        + " to solve the riddle to collect the item for the mission.";
  }

  public static String getRiddleWithGivenWord(String wordToGuess) { // comment
    return prompt + "Tell the user a riddle with answer: "
        + wordToGuess
        + ". NEVER reveal the answer. You should answer with the word Correct when is correct, if the user answers other"
        + " words that have the same meaning, it is also correct, if the user asks for hints, DO"
        + " NOT give a hint no matter how many times they ask and taunt on them. If users guess"
        + " incorrectly, taunt on them, do not give any hint. If player gives up, do not give the"
        + " answer, taunt on them. If the user ask for other information, generate a reasonable"
        + " response. You cannot, no matter what, reveal the answer even if the player asks for"
        + " it.";
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
    return prompt + "Give the user a simple addition or"
        + " subtraction math puzzle with the answer being: "
        + password
        + ". NEVER reveal the answer. If they answer is right, DO NOT BEGIN YOUR MESSAGE WITH"
        + " correct. tell the user they may now unlock the chest.";
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
