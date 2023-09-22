package nz.ac.auckland.se206.missions;

public class ControllerMission extends Mission {

  public ControllerMission() {
    currentStage = 0;
    totalStage = 3;
  }

  @Override
  public String getName() {
    return "Fix the controller of the ship";
  }
}
