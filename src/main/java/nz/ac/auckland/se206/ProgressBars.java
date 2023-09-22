package nz.ac.auckland.se206;

import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.MissionManager.MISSION;
import nz.ac.auckland.se206.SceneManager.AppPanel;

/** This class controls all progress bars */
public class ProgressBars {

  private MISSION taskOne;
  private MISSION taskTwo;

  /**
   * Set up the first mission in the progress bar
   *
   * @param taskOne The id of the first task
   */
  public void setMissionOne(int taskOne) {
    // If the first mission is window mission, set up the window mission progress bar
    if (taskOne == 1) {
      this.taskOne = MISSION.WINDOW;
      labelHelper("#topLabel", "Fix the window");
    } else if (taskOne == 2) {
      // If the first mission is fuel mission, set up the window mission progress bar
      this.taskOne = MISSION.FUEL;
      labelHelper("#topLabel", "Collect fuel for ship");
    }
  }

  /**
   * Set up the second mission in the progress bar
   *
   * @param taskOne The id of the first task
   */
  public void setMissionTwo(int taskTwo) {
    // If the second mission is controller mission, set up the window mission progress bar
    if (taskTwo == 3) {
      this.taskTwo = MISSION.CONTROLLER;
      labelHelper("#bottomLabel", "Fix the controller of the ship");
      // If the second mission is thruster mission, set up the window mission progress bar
    } else if (taskTwo == 4) {
      this.taskTwo = MISSION.THRUSTER;
      labelHelper("#bottomLabel", "Fix the thrusters of the ship");
    }
  }

  /**
   * Gets the MISSION enum of the first mission
   *
   * @return The MISSION enum of the first mission
   */
  public MISSION getMissionOne() {
    return taskOne;
  }

  /**
   * Gets the MISSION enum of the second mission
   *
   * @return The MISSION enum of the second mission
   */
  public MISSION getMissionTwo() {
    return taskTwo;
  }

  /**
   * Update the first ProgressBar
   *
   * @param mission a MISSION enum
   */
  public void updateProgressOne(MISSION mission) {
    // get the percentage of first mission
    int percentage = GameState.missionManager.getMission(mission).getPercentage();
    // Calculate the correct lentgh of the progress bar
    int midBarLength = (int) (percentage * 830) / 100;
    int outerBarLength = midBarLength + 6;
    // Set the progressbar in the progress panel to new length
    ((Rectangle) SceneManager.getPanel(AppPanel.PROGRESS).lookup("#topBarCenter"))
        .setWidth(midBarLength);
    ((Rectangle) SceneManager.getPanel(AppPanel.PROGRESS).lookup("#topBarTop"))
        .setWidth(outerBarLength);
    ((Rectangle) SceneManager.getPanel(AppPanel.PROGRESS).lookup("#topBarBottom"))
        .setWidth(outerBarLength);
  }

  /**
   * Update the second ProgressBar
   *
   * @param mission a MISSION enum
   */
  public void updateProgressTwo(MISSION mission) {
    // get the percentage of second mission
    int percentage = GameState.missionManager.getMission(mission).getPercentage();
    // Calculate the correct lentgh of the progress bar
    int midBarLength = (int) (percentage * 830) / 100;
    int outerBarLength = midBarLength + 6;
    // Set the progressbar in the progress panel to new length
    ((Rectangle) SceneManager.getPanel(AppPanel.PROGRESS).lookup("#bottomBarCenter"))
        .setWidth(midBarLength);
    ((Rectangle) SceneManager.getPanel(AppPanel.PROGRESS).lookup("#bottomBarTop"))
        .setWidth(outerBarLength);
    ((Rectangle) SceneManager.getPanel(AppPanel.PROGRESS).lookup("#bottomBarBottom"))
        .setWidth(outerBarLength);
  }

  /**
   * Update the task label of progress bar in progress bar panel
   *
   * @param Label a MISSION enum
   * @param text a MISSION enum
   */
  public void labelHelper(String Label, String text) {
    ((Label) SceneManager.getPanel(AppPanel.PROGRESS).lookup(Label)).setText(text);
  }
}
