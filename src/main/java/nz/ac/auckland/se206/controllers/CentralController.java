package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Polygon;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionManager.MISSION;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.TreeAvatar;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class CentralController {

  @FXML private Label counter;
  @FXML private Label guideLabel;
  @FXML private ImageView guideImage;
  @FXML private ImageView outside;
  @FXML private ImageView storage;
  @FXML private ImageView progressButton;
  @FXML private ImageView root1;
  @FXML private ImageView root2;
  @FXML private ImageView root3;
  @FXML private ImageView root4;
  @FXML private ImageView chest;
  @FXML private ImageView miniTree;
  @FXML private ImageView completeGame;

  @FXML private Polygon outsideDoor;
  @FXML private Polygon storageDoor;
  @FXML private Polygon window;
  @FXML private Polygon fuelTank;
  @FXML private Polygon controllerBroken1;
  @FXML private Polygon controllerBroken2;
  @FXML private Polygon rootOneCollisionBox1;
  @FXML private Polygon rootOneCollisionBox2;
  @FXML private Polygon rootOneCollisionBox3;
  @FXML private Polygon rootOneCollisionBox4;
  @FXML private Polygon rootTwoCollisionBox1;
  @FXML private Polygon rootTwoCollisionBox2;
  @FXML private Polygon rootTwoCollisionBox3;
  @FXML private Polygon rootTwoCollisionBox4;
  @FXML private Polygon rootThreeCollisionBox1;
  @FXML private Polygon rootThreeCollisionBox2;
  @FXML private Polygon rootThreeCollisionBox3;
  @FXML private Polygon rootThreeCollisionBox4;
  @FXML private Polygon rootThreeCollisionBox5;
  @FXML private Polygon rootFourCollisionBox1;
  @FXML private Polygon rootFourCollisionBox2;
  @FXML private Polygon rootFourCollisionBox3;
  @FXML private Polygon rootFourCollisionBox4;
  @FXML private Polygon rootFourCollisionBox5;
  @FXML private Polygon rootFourCollisionBox6;
  @FXML private Polygon rootFourCollisionBox7;
  @FXML private Polygon rootFourCollisionBox8;
  @FXML private Polygon rootFourCollisionBox9;

  @FXML private ImageView windowController1;
  @FXML private ImageView windowController2;
  @FXML private ImageView fuelController1;
  @FXML private ImageView fuelController2;
  @FXML private ImageView windowThruster1;
  @FXML private ImageView windowThruster2;
  @FXML private ImageView fuelThruster1;
  @FXML private ImageView fuelThruster2;
  @FXML private ImageView completeImage;

  @FXML private ImageView windowInfo;
  @FXML private ImageView fuelInfo;
  @FXML private ImageView controllerInfo;
  @FXML private Label infoLabel;
  @FXML private Label infoTitle;

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
    WinController.playMedia();

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
      // Remove the window collision box
      window.setOpacity(0);
      window.setDisable(true);
      // Initialise the second mission
      activateBlueprint();
      // activateChest();
      activateSecondMissionImage();
      // Show the fix window message
      // SceneManager.showDialog("Info", "Window fixed", "Mission accomplished");
      outsideDoor.setDisable(true);
      storageDoor.setDisable(true);
    } else {
      // If the inventory does not contain a window, show broken message
      // SceneManager.showDialog("Info", "Broken Window", "A large crack is inside the window!");
      infoTitle.setText("Broken Window");
      infoLabel.setText("A large crack is inside the window");
      infoTitle.setVisible(true);
      infoLabel.setVisible(true);
      windowInfo.setVisible(true);
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
      // activateChest();
      activateSecondMissionImage();
      // Show success message
      // SceneManager.showDialog("Info", "Fuel added", "Mission accomplished");
      outsideDoor.setDisable(true);
      storageDoor.setDisable(true);
    } else {
      // If the inventory does not contain fuel, show error message
      // SceneManager.showDialog("Info", "No Fuel", "Internal fuel tank is empty!");
      infoTitle.setText("No Fuel");
      infoLabel.setText("Internal fuel tank is empty");
      infoTitle.setVisible(true);
      infoLabel.setVisible(true);
      fuelInfo.setVisible(true);
    }
  }

  public void fixController() {
    // This method trys to fix the controller
    // If the controller mission less than stage 0 (not having spare part on hand), show broken
    // controller message
    if (GameState.missionManager.getMission(MISSION.CONTROLLER).getStage() != 1) {
      // SceneManager.showDialog(
      //     "Info", "Broken Control Panel", "The control panel for the ship is broken!");
      infoTitle.setText("Broken Control Panel");
      infoLabel.setText("The control panel for the ship is broken");
      infoTitle.setVisible(true);
      infoLabel.setVisible(true);
      controllerInfo.setVisible(true);
    } else if (GameState.missionManager.getMission(MISSION.CONTROLLER).getStage() == 1) {
      // If the controller mission is at stage 2, indicating panel can be fixed, show message
      // SceneManager.showDialog("Info", "Controller fixed", "Mission accomplished");
      // Increase the stage, update progress bar
      GameState.missionManager.getMission(MISSION.CONTROLLER).increaseStage();
      GameState.progressBarGroup.updateProgressTwo(MISSION.CONTROLLER);
      // Set the end game button visible
      completeGame.setDisable(false);
      completeGame.setVisible(true);
      GameState.isSecondMissionCompleted = true;
      // Show the complete image
      completeImage.setVisible(true);
      // Hide the controller
      controllerBroken1.setVisible(false);
      controllerBroken2.setVisible(false);
    }
  }

  // if window and control panel are fixed, then game can be completed by pressing red button
  public void goHome() {
    if (GameState.inventory.contains(6) && GameState.inventory.contains(7)) {
      App.setUi(AppPanel.WIN);

      WinController.playMedia();
    }
  }

  private void activateBlueprint() {
    if (!GameState.firstRiddleSolved || GameState.missionList.contains(3)) {
      return;
    }
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#blueprint").setVisible(true);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#blueprintCollisionBox").setVisible(true);
  }

  // private void activateChest() {
  //   if (!GameState.firstRiddleSolved || GameState.missionList.contains(4)) {
  //     return;
  //   }
  //   SceneManager.getPanel(AppPanel.STORAGE).lookup("#chest").setVisible(true);
  //   SceneManager.getPanel(AppPanel.STORAGE).lookup("#chest").setDisable(false);
  // }

  /** Activate the background image for second mission */
  private void activateSecondMissionImage() {
    if (GameState.missionList.contains(1) && GameState.missionList.contains(3)) {
      // Activate background image for window & controller mission
      windowController2.setVisible(true);
    } else if (GameState.missionList.contains(1) && GameState.missionList.contains(4)) {
      // Activate background image for window & thruster mission
      windowThruster2.setVisible(true);
    } else if (GameState.missionList.contains(2) && GameState.missionList.contains(3)) {
      // Activate background image for fuel & controller mission
      fuelController2.setVisible(true);
    } else {
      // Activate background image for fuel & thruster mission
      fuelThruster2.setVisible(true);
    }
  }

  public void activateProgressGlow() {
    progressButton.setEffect(GameState.glowBright);
  }

  public void deactivateProgressGlow() {
    progressButton.setEffect(GameState.glowDim);
  }

  public void activateDoorGlow() {
    outside.setEffect(GameState.glowBright);
    outsideDoor.setOpacity(1);
  }

  public void deactivateDoorGlow() {
    outside.setEffect(GameState.glowDim);
    outsideDoor.setOpacity(0);
  }

  public void activateFuelTankGlow() {
    fuelTank.setOpacity(1);
  }

  public void deactivateFuelTankGlow() {
    fuelTank.setOpacity(0);
  }

  public void activateStorageGlow() {
    storage.setEffect(GameState.glowBright);
    storageDoor.setOpacity(1);
  }

  public void deactivateStorageGlow() {
    storage.setEffect(GameState.glowDim);
    storageDoor.setOpacity(0);
  }

  public void activateWindowGlow() {
    window.setOpacity(1);
  }

  public void deactivateWindowGlow() {
    window.setOpacity(0);
  }

  public void activateControllerGlow() {
    controllerBroken1.setOpacity(1);
    controllerBroken2.setOpacity(1);
  }

  public void deactivateControllerGlow() {
    controllerBroken1.setOpacity(0);
    controllerBroken2.setOpacity(0);
  }

  public void goChat() {
    // This method set the panel to chat
    // Stop the tree flashing
    TreeAvatar.treeFlash.pause();
    TreeAvatar.deactivateTreeGlow();

    // If the first mission is completed, show the second guide message
    if (GameState.isFirstMissionCompleted && !GameState.isSecondGuideShown) {
      if (GameState.missionList.contains(1) && GameState.missionList.contains(4)) {
        String appendString =
            "You have repaired the window... Well done. You still cannot leave however, as the"
                + " thrusters are still damaged. In amongst your ship, I have hidden a"
                + " blueprint that should help you fix the thrusters";
        ((TextArea) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatTextArea"))
            .appendText("Wise Ancient Tree: " + appendString);
        ((Label) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatLabel")).setText(appendString);

      } else if (GameState.missionList.contains(1) && GameState.missionList.contains(3)) {

        String appendString =
            "You have repaired the window... Well done. You still cannot leave however, as the"
                + " control panel is still damaged. In amongst your ship, I have hidden a"
                + " chest containing spare parts that should help you fix the control panel";

        ((TextArea) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatTextArea"))
            .appendText("Wise Ancient Tree: " + appendString);
        ((Label) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatLabel")).setText(appendString);
        // SceneManager.getPanel(AppPanel.STORAGE).lookup("#chest").setVisible(true);
        //  SceneManager.getPanel(AppPanel.STORAGE).lookup("#chest").setDisable(false);
      } else if (GameState.missionList.contains(2) && GameState.missionList.contains(4)) {

        String appendString =
            "You have refueled the ship... Well done. You still cannot leave however, as the"
                + " thrusters are still damaged. In amongst your ship, I have hidden a"
                + " blueprint that should help you fix the thrusters";

        ((TextArea) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatTextArea"))
            .appendText("Wise Ancient Tree: " + appendString);
        ((Label) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatLabel")).setText(appendString);
      } else {

        String appendString =
            "You have refueled the ship... Well done. You still cannot leave however, as the"
                + " control panel is still damaged. In amongst your ship, I have hidden a"
                + " chest containing spare parts that should help you fix the control panel";

        ((TextArea) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatTextArea"))
            .appendText("Wise Ancient Tree: " + appendString);
        ((Label) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatLabel")).setText(appendString);
        //  SceneManager.getPanel(AppPanel.STORAGE).lookup("#chest").setVisible(true);
        // SceneManager.getPanel(AppPanel.STORAGE).lookup("#chest").setDisable(false);
      }
      outsideDoor.setDisable(false);
      storageDoor.setDisable(false);
    }
    GameState.isSecondGuideShown = true;

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

  public void hideGuide() {
    guideImage.setVisible(false);
    guideLabel.setVisible(false);
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   * @throws IOException
   * @throws ApiProxyException
   */
  @FXML
  public void okKeyPressed(KeyEvent event) throws ApiProxyException, IOException {
    if (event.getCode().toString().equals("ENTER") || event.getCode().toString().equals("ESCAPE")) {
      hideGuide();
    }
  }

  public void activateWinGlow() {
    completeGame.setEffect(GameState.glowBright);
  }

  public void deactivateWinGlow() {
    completeGame.setEffect(GameState.glowDim);
  }

  /* This method closes all info panel in this page */
  public void exitInfo() {
    infoLabel.setVisible(false);
    infoTitle.setVisible(false);
    windowInfo.setVisible(false);
    fuelInfo.setVisible(false);
    controllerInfo.setVisible(false);
  }

  public void activateRootOneGlow() {
    // uses set effect method to change the cursor visual to an open hand when the mouse is over the

    rootOneCollisionBox1.setOpacity(1);
    rootOneCollisionBox1.setCursor(Cursor.OPEN_HAND);
    rootOneCollisionBox2.setOpacity(1);
    rootOneCollisionBox2.setCursor(Cursor.OPEN_HAND);
    rootOneCollisionBox3.setOpacity(1);
    rootOneCollisionBox3.setCursor(Cursor.OPEN_HAND);
    rootOneCollisionBox4.setOpacity(1);
    rootOneCollisionBox4.setCursor(Cursor.OPEN_HAND);
  }

  public void deactivateRootOneGlow() {
    rootOneCollisionBox1.setOpacity(0);
    rootOneCollisionBox2.setOpacity(0);
    rootOneCollisionBox3.setOpacity(0);
    rootOneCollisionBox4.setOpacity(0);
  }

  public void activateRootTwoGlow() {
    rootTwoCollisionBox1.setOpacity(1);
    rootTwoCollisionBox1.setCursor(Cursor.OPEN_HAND);
    rootTwoCollisionBox2.setOpacity(1);
    rootTwoCollisionBox2.setCursor(Cursor.OPEN_HAND);
    rootTwoCollisionBox3.setOpacity(1);
    rootTwoCollisionBox3.setCursor(Cursor.OPEN_HAND);
    rootTwoCollisionBox4.setOpacity(1);
    rootTwoCollisionBox4.setCursor(Cursor.OPEN_HAND);
  }

  public void deactivateRootTwoGlow() {
    rootTwoCollisionBox1.setOpacity(0);
    rootTwoCollisionBox2.setOpacity(0);
    rootTwoCollisionBox3.setOpacity(0);
    rootTwoCollisionBox4.setOpacity(0);
  }

  public void activateRootThreeGlow() {
    rootThreeCollisionBox1.setOpacity(1);
    rootThreeCollisionBox1.setCursor(Cursor.OPEN_HAND);
    rootThreeCollisionBox2.setOpacity(1);
    rootThreeCollisionBox2.setCursor(Cursor.OPEN_HAND);
    rootThreeCollisionBox3.setOpacity(1);
    rootThreeCollisionBox3.setCursor(Cursor.OPEN_HAND);
    rootThreeCollisionBox4.setOpacity(1);
    rootThreeCollisionBox4.setCursor(Cursor.OPEN_HAND);
    rootThreeCollisionBox5.setOpacity(1);
    rootThreeCollisionBox5.setCursor(Cursor.OPEN_HAND);
  }

  public void deactivateRootThreeGlow() {
    rootThreeCollisionBox1.setOpacity(0);
    rootThreeCollisionBox2.setOpacity(0);
    rootThreeCollisionBox3.setOpacity(0);
    rootThreeCollisionBox4.setOpacity(0);
    rootThreeCollisionBox5.setOpacity(0);
  }

  public void activateRootFourGlow() {
    rootFourCollisionBox1.setOpacity(1);
    rootFourCollisionBox1.setCursor(Cursor.OPEN_HAND);
    rootFourCollisionBox2.setOpacity(1);
    rootFourCollisionBox2.setCursor(Cursor.OPEN_HAND);
    rootFourCollisionBox3.setOpacity(1);
    rootFourCollisionBox3.setCursor(Cursor.OPEN_HAND);
    rootFourCollisionBox4.setOpacity(1);
    rootFourCollisionBox4.setCursor(Cursor.OPEN_HAND);
    rootFourCollisionBox5.setOpacity(1);
    rootFourCollisionBox5.setCursor(Cursor.OPEN_HAND);
    rootFourCollisionBox6.setOpacity(1);
    rootFourCollisionBox6.setCursor(Cursor.OPEN_HAND);
    rootFourCollisionBox7.setOpacity(1);
    rootFourCollisionBox7.setCursor(Cursor.OPEN_HAND);
    rootFourCollisionBox8.setOpacity(1);
    rootFourCollisionBox8.setCursor(Cursor.OPEN_HAND);
    rootFourCollisionBox9.setOpacity(1);
    rootFourCollisionBox9.setCursor(Cursor.OPEN_HAND);
  }

  public void deactivateRootFourGlow() {
    rootFourCollisionBox1.setOpacity(0);
    rootFourCollisionBox2.setOpacity(0);
    rootFourCollisionBox3.setOpacity(0);
    rootFourCollisionBox4.setOpacity(0);
    rootFourCollisionBox5.setOpacity(0);
    rootFourCollisionBox6.setOpacity(0);
    rootFourCollisionBox7.setOpacity(0);
    rootFourCollisionBox8.setOpacity(0);
    rootFourCollisionBox9.setOpacity(0);
  }
}
