package nz.ac.auckland.se206.missions;

/** This is a mission class, contains of 3 total steps */
public class ThrusterMission extends Mission {

  public ThrusterMission() {
    currentStage = 0;
    totalStage = 3;
  }

  /**
   * Get the name of this mission
   *
   * @return the name of this mission.
   */
  @Override
  public String getName() {
    return "Fix the thrusters of the ship";
  }
}
