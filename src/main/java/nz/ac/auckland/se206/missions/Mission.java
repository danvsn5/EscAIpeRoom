package nz.ac.auckland.se206.missions;

public interface Mission {
  public abstract void initialize();

  public abstract String getName();

  public abstract void increaseStage();

  public abstract int getStage();

  public abstract int getPercentage();

  public abstract void askGpt();
}
