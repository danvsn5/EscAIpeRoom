package nz.ac.auckland.se206;

import nz.ac.auckland.se206.SceneManager.AppPanel;

public class MissionInitialise {

  public void initialiseFirstMission(int taskOne) {
    if (taskOne == 1) {
      // initialise window mission
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#window").setVisible(true);
    } else {
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#fuelTank").setVisible(true);
    }
  }

  public void initialiseSecondMission(int taskTwo) {
    if (taskTwo == 2) {
      // initialise chest mission
    } else {
      // initialise thruster mission
    }
  }
}
