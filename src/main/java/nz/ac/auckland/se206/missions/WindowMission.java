package nz.ac.auckland.se206.missions;

/** This is a mission class, contains of 4 total steps */
public class WindowMission extends Mission {

  public WindowMission() {
    currentStage = 0;
    totalStage = 4;
  }

  /**
   * Get the name of this mission
   *
   * @return the name of this mission.
   */
  @Override
  public String getName() {
    return "Fix the window";
  }
}
