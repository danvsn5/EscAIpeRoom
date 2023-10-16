package nz.ac.auckland.se206.missions;

/** This is an abstract class, every Mission class extends this class. */
public abstract class Mission {
  protected int currentStage;
  protected int totalStage;

  /**
   * Get the name of this mission.
   *
   * @return the name of this mission.
   */
  public abstract String getName();

  /** Increase the current stage of this mission by 1. */
  public void increaseStage() {
    // Increase the currentStage
    currentStage++;
    // If the currentStage is more than total stage, something goes wrong
    if (currentStage > totalStage) {
      System.out.println("Mission already finished, can't increase stage");
      // Reset the currentStage
      currentStage--;
    }
  }

  /**
   * Get the current stage of this mission.
   *
   * @return the currentStage of this mission.
   */
  public int getStage() {
    return currentStage;
  }

  /**
   * Get the progress of this mission.
   *
   * @return the progress of this mission.
   */
  public int getPercentage() {
    return currentStage * 100 / totalStage;
  }

  /**
   * Check if this mission ends.
   *
   * @return if this mission ends.
   */
  public boolean isEnd() {
    return currentStage == totalStage;
  }
}
