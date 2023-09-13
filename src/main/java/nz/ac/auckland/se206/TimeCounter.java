package nz.ac.auckland.se206;

public class TimeCounter {
  private int secondCounter;
  private int minuteCounter;

  /** The constructor of the class, initialize a time counter */
  public TimeCounter(int minute, int second) {
    secondCounter = second;
    minuteCounter = minute;
  }

  /** Decrease the counter by 1 */
  public void decrease() {
    // Decrease the second counter
    secondCounter--;
    // If 1 minute passes, decrease the minute counter and restore the second counter
    if (secondCounter == -1) {
      minuteCounter--;
      secondCounter = 59;
    }
  }

  /**
   * Return the status of the counter
   *
   * @return a boolean indicate if the countdown is finished
   */
  public boolean isEnd() {
    return minuteCounter < 0;
  }

  /**
   * Return the second counter
   *
   * @return int value indicates how many seconds left
   */
  public int getSecond() {
    return secondCounter;
  }

  /**
   * Return the minute counter
   *
   * @return int value indicates how many minutes left
   */
  public int getMinute() {
    return minuteCounter;
  }
}
