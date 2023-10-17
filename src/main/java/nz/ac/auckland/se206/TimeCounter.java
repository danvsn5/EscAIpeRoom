package nz.ac.auckland.se206;

import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.controllers.LoseController;

/** This class counts down from given time. */
public class TimeCounter {
  private int secondCounter;
  private int minuteCounter;
  private double totalSeconds;

  /**
   * The constructor of the class, initialize a time counter.
   *
   * @param minute The minute of the countdown.
   * @param second The second of the countdown.
   */
  public TimeCounter(int minute, int second) {
    secondCounter = second;
    minuteCounter = minute;
    totalSeconds = second + minute * 60;
  }

  /**
   * Get current countdown time in format of minutes:seconds.
   *
   * @return the formated time.
   */
  public String getTime() {
    // If the countdown ends, return "END"
    if (minuteCounter == 0 && secondCounter == 0) {
      LoseController.playMedia();
      App.setUi(AppPanel.LOSE);
      return "END";
    }
    // If second counter is between 0 to 9, add another 0 in front of it
    if (secondCounter >= 0 && secondCounter <= 9) {
      return (minuteCounter + ":0" + secondCounter);
    }
    return (minuteCounter + ":" + secondCounter);
  }

  /** Decrease the counter by 1. */
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
   * Return the status of the counter.
   *
   * @return a boolean indicate if the countdown is finished.
   */
  public boolean isEnd() {
    return minuteCounter < 0;
  }

  /**
   * Return the second counter, it represents the second left.
   *
   * @return int value indicates how many seconds left.
   */
  public int getSecond() {
    return secondCounter;
  }

  /**
   * Return the minute counter, it represents the minute left.
   *
   * @return int value indicates how many minutes left.
   */
  public int getMinute() {
    return minuteCounter;
  }

  /**
   * Return the remaining time in percentage * 100.
   *
   * @return double value of the remaining time in percentage * 100.
   */
  public double getRemainingPercentage() {
    // Calculates the current time in seconds
    double currentSeconds = secondCounter + minuteCounter * 60 + 1;
    return currentSeconds * 100 / totalSeconds;
  }

  /** Set the timer to finish state. */
  public void setFinish() {
    secondCounter = -1;
    minuteCounter = -1;
  }
}
