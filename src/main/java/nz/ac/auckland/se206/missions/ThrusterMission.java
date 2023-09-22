package nz.ac.auckland.se206.missions;

public class ThrusterMission extends Mission {

  public ThrusterMission() {
    currentStage = 0;
    totalStage = 3;
  }

  @Override
  public String getName() {
    return "Fix the thrusters of the ship";
  }
}
