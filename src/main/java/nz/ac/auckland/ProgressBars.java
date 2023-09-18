package nz.ac.auckland;


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
    } else if (taskOne == 1) {
      this.taskOne = MISSION.CONTROLLER;
    } else if (taskOne == 2) {
      this.taskOne = MISSION.FUEL;
    } else if (taskOne == 3) {
      this.taskOne = MISSION.THRUSTER;
    }
  }

  public void setMissionTwo(int taskTwo) {

    if (taskTwo == 0) {
      this.taskTwo = MISSION.WINDOW;
    } else if (taskTwo == 1) {
      this.taskTwo = MISSION.CONTROLLER;
    } else if (taskTwo == 2) {
      this.taskTwo = MISSION.FUEL;
    } else if (taskTwo == 3) {
      this.taskTwo = MISSION.THRUSTER;
    }
  }

  public MISSION getTaskOne() {
    return taskOne;
  }

  public MISSION getTaskTwo() {
    return taskTwo;
  }
}
