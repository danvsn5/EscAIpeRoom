package nz.ac.auckland.se206;

import nz.ac.auckland.se206.SceneManager.AppPanel;

/**
 * The MissionInitialise class initializes the missions based on the given task numbers 
 * and combines the task codes to initialize the main room.
 */
public class MissionInitialise {
  private int combineCode = 0;

  /**
   * Initializes the first mission based on the given task number.
   * If the task number is 1, the window mission is initialized and the window 
   * and process machine are made visible and enabled.
   * If the task number is not 1, the fuel tank mission is initialized and the fuel tank 
   * is made visible and enabled.
   *
   * @param taskOne the task number for the first mission
   */
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

  /** 
   * Combines the given taskTwo code with the existing code and initializes the second mission.
   * If taskTwo is 3, initializes the controller mission by making certain elements visible 
   * and enabled in the main room, storage, and outside panels.
   * Otherwise, initializes the thruster mission by making certain elements visible and enabled 
   * in the storage and outside panels.
   * Finally, initializes the main room.
   *
   * @param taskTwo the code for the second task
   */
  public void initialiseSecondMission(int taskTwo) {
    combineCode += taskTwo; // combine the code
    if (taskTwo == 3) { // if the task two is 3
      // initialise controller mission
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#controllerBroken1").setVisible(true);
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#controllerBroken1").setDisable(false);
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#controllerBroken2").setVisible(true);
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#controllerBroken2").setDisable(false);
      SceneManager.getPanel(AppPanel.STORAGE).lookup("#controller").setVisible(true);
      SceneManager.getPanel(AppPanel.STORAGE).lookup("#controller").setDisable(false);
      SceneManager.getPanel(AppPanel.STORAGE).lookup("#chest").setVisible(true);
      SceneManager.getPanel(AppPanel.STORAGE).lookup("#chest").setDisable(false);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#shipDoor1").setVisible(true);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#shipDoor1").setDisable(false);
    } else { // else
      SceneManager.getPanel(AppPanel.STORAGE).lookup("#thruster").setVisible(true);
      SceneManager.getPanel(AppPanel.STORAGE).lookup("#thruster").setDisable(false);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#outsideBrokenImage").setVisible(true);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#outsideBrokenImage").setDisable(false);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#thruster1").setVisible(true);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#thruster1").setDisable(false);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#thruster2").setVisible(true);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#thruster2").setDisable(false);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#shipDoor2").setVisible(true);
      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#shipDoor2").setDisable(false);
    }
    initialiseMainRoom(); // initialise the main room
  }

  private void initialiseMainRoom() {
    if (combineCode == 13) { // if the combine code is 13
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#windowController1").setVisible(true);
    } else if (combineCode == 23) { // if the combine code is 23
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#fuelController1").setVisible(true);
    } else if (combineCode == 14) { // if the combine code is 14
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#windowThruster1").setVisible(true);
    } else { // if the combine code is 24
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#fuelThruster1").setVisible(true);
    }
  }
}
