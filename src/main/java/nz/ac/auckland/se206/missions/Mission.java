package nz.ac.auckland.se206.missions;

import nz.ac.auckland.se206.gpt.ChatMessage;

public interface Mission {
  public abstract void initialize();

  public abstract String getName();

  public abstract void increaseStage();

  public abstract int getStage();

  public abstract int getPercentage();

  public abstract ChatMessage askGpt();
}
