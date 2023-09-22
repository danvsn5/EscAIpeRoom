package nz.ac.auckland.se206.missions;


public abstract class Mission {
  protected int currentStage;
  protected int totalStage;

  public abstract String getName();

  public void increaseStage() {
    currentStage++;
    if (currentStage > totalStage) {
      System.out.println("Mission already finished, can't increase stage");
      currentStage--;
    }
  }

  public int getStage() {
    return currentStage;
  }

  public int getPercentage() {
    return currentStage * 100 / totalStage;
  }

  public boolean isEnd() {
    return currentStage == totalStage;
  }
}
