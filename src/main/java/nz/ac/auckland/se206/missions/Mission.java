package nz.ac.auckland.se206.missions;

import nz.ac.auckland.se206.gpt.ChatMessage;

public abstract class Mission {
  protected int currentStage;
  protected int totalStage;

  public abstract void initialize();

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
  ;

  public int getPercentage() {
    return currentStage * 100 / totalStage;
  }
  ;

  public abstract ChatMessage askGpt();

  public abstract void updateProgress();
}
