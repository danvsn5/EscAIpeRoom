package nz.ac.auckland.se206;

import nz.ac.auckland.se206.SceneManager.AppPanel;

public class MissionInitialise {
  private int combineCode = 0;

  public void initialiseFirstMission(int taskOne) {
    combineCode = taskOne * 10;
    if (taskOne == 1) {
      // initialise window mission
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#window").setVisible(true);
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#window").setDisable(false);
      SceneManager.getPanel(AppPanel.STORAGE).lookup("#processMachine").setVisible(true);
      SceneManager.getPanel(AppPanel.STORAGE).lookup("#processMachine").setDisable(false);
    } else {
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#fuelTank").setVisible(true);
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#fuelTank").setDisable(false);
    }
  }

  public void initialiseSecondMission(int taskTwo) {
    combineCode += taskTwo;
    if (taskTwo == 3) {
      // initialise controller mission
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#controllerBroken1").setVisible(true);
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#controllerBroken1").setDisable(false);
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#controllerBroken2").setVisible(true);
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#controllerBroken2").setDisable(false);
      SceneManager.getPanel(AppPanel.STORAGE).lookup("#controller").setVisible(true);
      SceneManager.getPanel(AppPanel.STORAGE).lookup("#controller").setDisable(false);
      SceneManager.getPanel(AppPanel.STORAGE).lookup("#chest").setVisible(true);
      SceneManager.getPanel(AppPanel.STORAGE).lookup("#chest").setDisable(false);
    } else {
      SceneManager.getPanel(AppPanel.STORAGE).lookup("#thruster").setVisible(true);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#outsideBrokenImage").setVisible(true);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#outsideBrokenImage").setDisable(false);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#thruster1").setVisible(true);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#thruster1").setDisable(false);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#thruster2").setVisible(true);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#thruster2").setDisable(false);
    }
    initialiseMainRoom();
  }

  private void initialiseMainRoom() {
    if (combineCode == 13) {
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#windowController1").setVisible(true);
    } else if (combineCode == 23) {
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#fuelController1").setVisible(true);
    } else if (combineCode == 14) {
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#windowThruster1").setVisible(true);
    } else {
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#fuelThruster1").setVisible(true);
    }
  }
}
