package nz.ac.auckland;

import javafx.scene.control.Label;
import nz.ac.SceneManager;
import nz.ac.SceneManager.AppPanel;

public class ProgressBars {

  public enum MISSION {
    WINDOW,
    CONTROLLER,
    FUEL,
    THRUSTER
  }

  MISSION taskOne;
  MISSION taskTwo;

  public ProgressBars() {}

  public void setMissionOne(int taskOne) {

    if (taskOne == 0) {
      this.taskOne = MISSION.WINDOW;
      labelHelper("#bottomLabel", "Fix the window");
    } else if (taskOne == 1) {
      this.taskOne = MISSION.CONTROLLER;
      labelHelper("#bottomLabel", "Fix the controller of the ship");
    } else if (taskOne == 2) {
      this.taskOne = MISSION.FUEL;
      labelHelper("#bottomLabel", "Collect fuel for ship");
    } else if (taskOne == 3) {
      this.taskOne = MISSION.THRUSTER;
      labelHelper("#bottomLabel", "Fix the thrusters of the ship");
    }
  }

  public void setMissionTwo(int taskTwo) {

    if (taskTwo == 0) {
      this.taskTwo = MISSION.WINDOW;
      labelHelper("#bottomLabel", "Fix the window");
    } else if (taskTwo == 1) {
      this.taskTwo = MISSION.CONTROLLER;
      labelHelper("#bottomLabel", "Fix the controller of the ship");
    } else if (taskTwo == 2) {
      this.taskTwo = MISSION.FUEL;
      labelHelper("#bottomLabel", "Collect fuel for ship");
    } else if (taskTwo == 3) {
      this.taskTwo = MISSION.THRUSTER;
      labelHelper("#bottomLabel", "Fix the thrusters of the ship");
    }
  }

  public MISSION getTaskOne() {
    return taskOne;
  }

  public MISSION getTaskTwo() {
    return taskTwo;
  }

  public void labelHelper(String Label, String text) {
    ((Label) SceneManager.getPanel(AppPanel.PROGRESS).lookup(Label)).setText(text);
  }
}
