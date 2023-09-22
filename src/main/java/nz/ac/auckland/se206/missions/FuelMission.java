package nz.ac.auckland.se206.missions;

public class FuelMission extends Mission {

  public FuelMission() {
    currentStage = 0;
    totalStage = 3;
  }

  @Override
  public String getName() {
    return "Collect fuel for ship";
  }
}
