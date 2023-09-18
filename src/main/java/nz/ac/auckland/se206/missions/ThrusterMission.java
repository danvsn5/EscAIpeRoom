package nz.ac.auckland.se206.missions;

import nz.ac.auckland.se206.gpt.ChatMessage;

public class ThrusterMission extends Mission {

  public ThrusterMission() {
    currentStage = 0;
    totalStage = 2;
  }

  @Override
  public void initialize() {
    // TODO Set the correct image to visible
  }

  @Override
  public String getName() {
    return "Fix the thrusters of the ship";
  }

  @Override
  public ChatMessage askGpt() {
    // TODO ask gpt to generate riddle
    throw new UnsupportedOperationException("Unimplemented method 'askGpt'");
  }
}
