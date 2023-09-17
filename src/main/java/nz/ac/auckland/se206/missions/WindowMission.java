package nz.ac.auckland.se206.missions;

import nz.ac.auckland.se206.gpt.ChatMessage;

public class WindowMission extends Mission {

  public WindowMission() {
    currentStage = 0;
    totalStage = 3;
  }

  @Override
  public void initialize() {
    // TODO Set the correct image to visible
  }

  @Override
  public String getName() {
    return "Fix the window";
  }

  @Override
  public ChatMessage askGpt() {
    // TODO ask gpt to generate riddle
    throw new UnsupportedOperationException("Unimplemented method 'askGpt'");
  }
}
