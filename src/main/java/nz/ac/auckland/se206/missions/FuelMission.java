package nz.ac.auckland.se206.missions;

import nz.ac.auckland.se206.controllers.ChatController;
import nz.ac.auckland.se206.gpt.ChatMessage;

public class FuelMission extends Mission {

  ChatController chatController;

  public FuelMission() {
    currentStage = 0;
    totalStage = 3;
  }

  @Override
  public void initialize() {
    // TODO Set the correct image to visible
  }

  @Override
  public String getName() {
    return "Collect fuel for ship";
  }

  @Override
  public ChatMessage askGpt() {
    // TODO ask gpt to generate riddle
    return null;
  }
}
