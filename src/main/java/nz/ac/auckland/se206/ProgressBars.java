package nz.ac.auckland.se206;

import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import nz.ac.SceneManager;
import nz.ac.SceneManager.AppPanel;

public class ProgressBars {

  private enum MISSION {
    WINDOW,
    CONTROLLER,
    FUEL,
    THRUSTER
  }

  MISSION taskOne;
  MISSION taskTwo;

  public ProgressBars() {}

  public void setMissionOne(int taskOne) {

    if (taskOne == 1) {
      this.taskOne = MISSION.WINDOW;
      labelHelper("#topLabel", "Fix the window");
    } else if (taskOne == 2) {
      this.taskOne = MISSION.CONTROLLER;
      labelHelper("#topLabel", "Fix the controller of the ship");
    } else if (taskOne == 3) {
      this.taskOne = MISSION.FUEL;
      labelHelper("#topLabel", "Collect fuel for ship");
    } else if (taskOne == 4) {
      this.taskOne = MISSION.THRUSTER;
      labelHelper("#topLabel", "Fix the thrusters of the ship");
    }
  }

  public void setMissionTwo(int taskTwo) {

    if (taskTwo == 1) {
      this.taskTwo = MISSION.WINDOW;
      labelHelper("#bottomLabel", "Fix the window");
    } else if (taskTwo == 2) {
      this.taskTwo = MISSION.CONTROLLER;
      labelHelper("#bottomLabel", "Fix the controller of the ship");
    } else if (taskTwo == 3) {
      this.taskTwo = MISSION.FUEL;
      labelHelper("#bottomLabel", "Collect fuel for ship");
    } else if (taskTwo == 4) {
      this.taskTwo = MISSION.THRUSTER;
      labelHelper("#bottomLabel", "Fix the thrusters of the ship");
    }
  }

  public MISSION getMissionOne() {
    return taskOne;
  }

  public MISSION getMissionTwo() {
    return taskTwo;
  }

  public void updateProgressOne(nz.ac.auckland.se206.MissionManager.MISSION mission) {
    int percentage = GameState.missionManager.getMission(mission).getPercentage();
    int midBarLength = (int) (percentage * 830);
    int outerBarLength = (int) (percentage * 836);
    ((Rectangle) SceneManager.getPanel(AppPanel.PROGRESS).lookup("#topBarCenter"))
        .setWidth(midBarLength);
    ((Rectangle) SceneManager.getPanel(AppPanel.PROGRESS).lookup("#topBarTop"))
        .setWidth(outerBarLength);
    ((Rectangle) SceneManager.getPanel(AppPanel.PROGRESS).lookup("#topBarBottom"))
        .setWidth(outerBarLength);
  }

  public void updateProgressTwo(nz.ac.auckland.se206.MissionManager.MISSION mission) {
    int percentage = GameState.missionManager.getMission(mission).getPercentage();
    int midBarLength = (int) (percentage * 830);
    int outerBarLength = (int) (percentage * 836);
    ((Rectangle) SceneManager.getPanel(AppPanel.PROGRESS).lookup("#bottomBarCenter"))
        .setWidth(midBarLength);
    ((Rectangle) SceneManager.getPanel(AppPanel.PROGRESS).lookup("#bottomBarTop"))
        .setWidth(outerBarLength);
    ((Rectangle) SceneManager.getPanel(AppPanel.PROGRESS).lookup("#bottomBarBottom"))
        .setWidth(outerBarLength);
  }

  public void labelHelper(String Label, String text) {
    ((Label) SceneManager.getPanel(AppPanel.PROGRESS).lookup(Label)).setText(text);
  }
}
