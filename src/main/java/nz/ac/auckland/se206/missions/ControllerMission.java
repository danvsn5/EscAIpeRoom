package nz.ac.auckland.se206.missions;

/** This is a mission class, contains of 3 total steps */
public class ControllerMission extends Mission {

  public ControllerMission() {
    currentStage = 0;
    totalStage = 2;
  }

  /**
   * Get the name of this mission
   *
   * @return the name of this mission.
   */
  @Override
  public String getName() {
    return "Fix the controller of the ship";
  }
}
