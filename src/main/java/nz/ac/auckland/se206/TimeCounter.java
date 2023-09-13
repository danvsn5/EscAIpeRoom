package nz.ac.auckland.se206;

public class TimeCounter {
  private int secondCounter;
  private int minuteCounter;

  /** The constructor of the class, initialize a time counter */
  public TimeCounter(int minute, int second) {
    secondCounter = second;
    minuteCounter = minute;
  }
}
