package nz.ac.auckland.se206;

import nz.ac.auckland.se206.SceneManager.AppPanel;

public class MissionInitialise {

  public void initialiseFirstMission(int taskOne) {
    if (taskOne == 1) {
      // initialise window mission
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#window").setVisible(true);
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#window").setDisable(false);
    } else {
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#fuelTank").setVisible(true);
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#fuelTank").setDisable(false);
    }
  }

  public void initialiseSecondMission(int taskTwo) {
    if (taskTwo == 3) {
      // initialise chest mission
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#controllerBroken1").setVisible(true);
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#controllerBroken2").setDisable(false);
    } else {
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#thrusterWarning").setVisible(true);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#thrusterWarning").setDisable(false);
    }
  }
}
