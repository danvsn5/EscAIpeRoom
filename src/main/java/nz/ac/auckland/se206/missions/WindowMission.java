package nz.ac.auckland.se206.missions;

public class WindowMission extends Mission {

  public WindowMission() {
    currentStage = 0;
    totalStage = 4;
  }

  @Override
  public String getName() {
    return "Fix the window";
  }
}
