package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionManager.MISSION;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.TreeAvatar;

public class CentralController {

  @FXML private Button okButton;
  @FXML private Label counter;
  @FXML private Label guideLabel;
  @FXML private ImageView storage;
  @FXML private ImageView progressButton;
  @FXML private ImageView rootInitial;
  @FXML private ImageView rootOne;
  @FXML private ImageView rootTwo;
  @FXML private ImageView rootThree;
  @FXML private ImageView chest;
  @FXML private ImageView miniTree;
  @FXML private ImageView fuelTank;
  @FXML private ImageView controller;
  @FXML private ImageView completeGame;
  @FXML private Rectangle guideWindow;

  @FXML private Polygon outsideDoor;
  @FXML private Polygon window;

  public void goOutside() {
    SceneManager.setPrevious(AppPanel.MAIN_ROOM);
    App.setUi(AppPanel.OUTSIDE);
  }

  public void goProgress() {
    SceneManager.setPrevious(AppPanel.MAIN_ROOM);
    App.setUi(AppPanel.PROGRESS);
  }

  public void goStorage() {
    App.setUi(AppPanel.STORAGE);
  }

  public void goWin() {
    LaunchController.timer.setFinish();
    App.setUi(AppPanel.WIN);
  }

  // if inventory contains the necessary items, fixes the window and control panel and changes
  // visibility of assets
  public void repairWindow() {
    // If the inventory contains window
    if (GameState.inventory.contains(3)) {
      // Increase the stage of the window mission and update progress bar
      GameState.missionManager.getMission(MISSION.WINDOW).increaseStage();
      GameState.progressBarGroup.updateProgressOne(MISSION.WINDOW);
      // Record that the first mission is completed
      GameState.isFirstMissionCompleted = true;
      // Tree start flashing
      TreeAvatar.startFlashTree();
      // Remove the warning message
      window.setOpacity(0);
      window.setDisable(true);
      // Initialise the second mission
      activateBlueprint();
      activateChest();
      // Show the fix window message
      SceneManager.showDialog("Info", "Window fixed", "Mission accomplished");
    } else {
      // If the inventory does not contain a window, show broken message
      SceneManager.showDialog("Info", "Broken Window", "A large crack is inside the window!");
    }
  }

  public void addFuel() {
    // This method adds fuel to the ship
    if (GameState.inventory.contains(8)) {
      // If the inventory contains fuel, increase missing stage and fill up the ship
      GameState.missionManager.getMission(MISSION.FUEL).increaseStage();
      GameState.progressBarGroup.updateProgressOne(MISSION.FUEL);
      // Record that the first mission is completed
      GameState.isFirstMissionCompleted = true;
      // Remove fuel from inventory
      GameState.inventory.remove(GameState.inventory.indexOf(8));
      // Tree start flashing
      TreeAvatar.startFlashTree();
      // Hide the fuel warning
      fuelTank.setOpacity(0);
      fuelTank.setDisable(true);
      // Initialise the second mission
      activateBlueprint();
      activateChest();
      // Show success message
      SceneManager.showDialog("Info", "Fuel added", "Mission accomplished");
    } else {
      // If the inventory does not contain fuel, show error message
      SceneManager.showDialog("Info", "No Fuel", "Internal fuel tank is empty!");
    }
  }

  public void fixController() {
    // This method trys to fix the controller
    // If the controller mission less than stage 0 (not having spare part on hand), show broken
    // controller message
    if (GameState.missionManager.getMission(MISSION.CONTROLLER).getStage() != 1) {
      SceneManager.showDialog(
          "Info", "Broken Control Panel", "The control panel for the ship is broken!");
    } else if (GameState.missionManager.getMission(MISSION.CONTROLLER).getStage() == 1) {
      // If the controller mission is at stage 2, indicating panel can be fixed, show message
      SceneManager.showDialog("Info", "Controller fixed", "Mission accomplished");
      // Increase the stage, update progress bar
      GameState.missionManager.getMission(MISSION.CONTROLLER).increaseStage();
      GameState.progressBarGroup.updateProgressTwo(MISSION.CONTROLLER);
      // Set the end game button visible
      completeGame.setDisable(false);
      completeGame.setVisible(true);
      GameState.isSecondMissionCompleted = true;
    }
  }

  // if window and control panel are fixed, then game can be completed by pressing red button
  public void goHome() {
    if (GameState.inventory.contains(6) && GameState.inventory.contains(7)) {
      App.setUi(AppPanel.WIN);
    }
  }

  private void activateBlueprint() {
    if (!GameState.firstRiddleSolved || GameState.missionList.contains(3)) {
      return;
    }
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#blueprint").setVisible(true);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#blueprint").setDisable(false);
  }

  private void activateChest() {
    if (!GameState.firstRiddleSolved || GameState.missionList.contains(4)) {
      return;
    }
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#chest").setVisible(true);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#chest").setDisable(false);
  }

  public void activateProgressGlow() {
    progressButton.setEffect(GameState.glowBright);
  }

  public void deactivateProgressGlow() {
    progressButton.setEffect(GameState.glowDim);
  }

  public void activateDoorGlow() {
    outsideDoor.setOpacity(1);
  }

  public void deactivateDoorGlow() {
    outsideDoor.setOpacity(0);
  }

  public void activateFuelTankGlow() {
    fuelTank.setEffect(GameState.glowBright);
  }

  public void deactivateFuelTankGlow() {
    fuelTank.setEffect(GameState.glowDim);
  }

  public void activateStorageGlow() {
    storage.setEffect(GameState.glowBright);
  }

  public void deactivateStorageGlow() {
    storage.setEffect(GameState.glowDim);
  }

  public void activateWindowGlow() {
    window.setOpacity(1);
  }

  public void deactivateWindowGlow() {
    window.setOpacity(0);
  }

  public void activateControllerGlow() {
    controller.setEffect(GameState.glowBright);
  }

  public void deactivateControllerGlow() {
    controller.setEffect(GameState.glowDim);
  }

  public void goChat() {
    // This method set the panel to chat
    // Stop the tree flashing
    TreeAvatar.treeFlash.pause();
    TreeAvatar.deactivateTreeGlow();

    // If the first mission is completed, show the second guide message
    if (GameState.isFirstMissionCompleted) {
      if (GameState.missionList.contains(1) && GameState.missionList.contains(4)) {
        ((TextArea) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatTextArea"))
            .appendText(
                "You have repaired the window... Well done. You still cannot leave however, as the"
                    + " thrusters are still damaged. In amongst your ship, I have hidden a"
                    + " blueprint that should help you fix the thrusters");
      } else if (GameState.missionList.contains(1) && GameState.missionList.contains(3)) {
        ((TextArea) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatTextArea"))
            .appendText(
                "You have repaired the window... Well done. You still cannot leave however, as the"
                    + " control panel is still damaged. In amongst your ship, I have hidden a"
                    + " chest containing spare parts that should help you fix the control panel");
      } else if (GameState.missionList.contains(2) && GameState.missionList.contains(4)) {
        ((TextArea) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatTextArea"))
            .appendText(
                "You have refueled the ship... Well done. You still cannot leave however, as the"
                    + " thrusters are still damaged. In amongst your ship, I have hidden a"
                    + " blueprint that should help you fix the thrusters");
      } else {
        ((TextArea) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatTextArea"))
            .appendText(
                "You have refueled the ship... Well done. You still cannot leave however, as the"
                    + " control panel is still damaged. In amongst your ship, I have hidden a"
                    + " chest containing spare parts that should help you fix the control panel");
      }
    }

    // Set the previous panel to Main room then go to chat room
    SceneManager.setPrevious(AppPanel.MAIN_ROOM);
    App.setUi(AppPanel.CHAT);
  }

  public void miniTreeGlow() {
    miniTree.setEffect(GameState.glowBright);
  }

  public void miniTreeDim() {
    miniTree.setEffect(GameState.glowDim);
  }

  public void okBtnPressed() {
    guideWindow.setVisible(false);
    guideLabel.setVisible(false);
    okButton.setVisible(false);
    okButton.setDisable(true);
  }

  public void activateWinGlow() {
    completeGame.setEffect(GameState.glowBright);
  }

  public void deactivateWinGlow() {
    completeGame.setEffect(GameState.glowDim);
  }
}
