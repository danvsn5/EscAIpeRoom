package nz.ac.auckland.se206.gpt;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  // all calls will be done immediately with different thread so that when they need to be shown to
  // the screen by changing the labels of text, no time is wasted and the GUI does not freeze.
  public static String introCall() {
    return "Act like a wise mystical tree of a forest. Greet me and ask if i want to solve the"
        + " riddle to collect the item for the mission.";
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

  public static String getRiddleWithGivenWordWindow(String wordToGuess) {
    return "Act like a wise mystical tree of a forest. Tell me a riddle with answer: "
        + wordToGuess
        + ". The answer to the riddle MUST be: "
        + wordToGuess
        + ". Do not accept any other answers as correct. NEVER reveal the answer. You should answer"
        + " with the word 'Correct'. If the answer is incorrect, you should say it is incorrect."
        + " You can NEVER reveal the answer in any response or sentence, even if the player asks"
        + " for it. Even if the player gives up or gets incorrect, do not give the answer. When the"
        + " user gusses correctly, tell them to go back and collect the answer.";
  }

  public static String getRiddleWithGivenWordFuel(String wordToGuess, String wordToGuess2) {
    return "Act like a wise mystical tree of a forest. Tell me a riddle with answer: "
        + wordToGuess
        + ". NEVER reveal the answer. "
        + wordToGuess2
        + " is not an answer for the first riddle. You should answer with the phrase 'You are"
        + " right' when is correct. Then if the user gusses correctly, give the second riddle with"
        + " answer: "
        + wordToGuess2
        + " . You should answer with the word 'Correct' when is correct. If the answer is"
        + " incorrect, you should say it is incorrect. You can NEVER reveal the"
        + " answers in any response or sentence, even if the player asks for it. Even if player"
        + " gives up, do not give the answer.";
  }

  public static String getGuideToSecondMission(String mission) {
    return "Act like a wise mystical tree of a forest. Tell the user to move on to next mission."
               + " Next mission is: "
        + mission
        + ". ";
  }
}
