package nz.ac.auckland.se206.controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionManager.MISSION;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.TreeAvatar;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;

public class StorageController {

  @FXML private ImageView progressButton;
  @FXML private ImageView storageDoor;
  @FXML private ImageView hiddenChestImage;
  @FXML private ImageView blueprint;
  @FXML private ImageView miniTree;
  @FXML private ImageView root1;
  @FXML private ImageView root2;
  @FXML private ImageView root3;
  @FXML private ImageView root4;
  @FXML private Polygon storageRootOneCollisionBox1;
  @FXML private Polygon storageRootOneCollisionBox2;
  @FXML private Polygon storageRootOneCollisionBox3;
  @FXML private Polygon storageRootOneCollisionBox4;
  @FXML private Polygon storageRootTwoCollisionBox1;
  @FXML private Polygon storageRootTwoCollisionBox2;
  @FXML private Polygon storageRootTwoCollisionBox3;
  @FXML private Polygon storageRootTwoCollisionBox4;
  @FXML private Polygon storageRootThreeCollisionBox1;
  @FXML private Polygon storageRootThreeCollisionBox2;
  @FXML private Polygon storageRootThreeCollisionBox3;
  @FXML private Polygon storageRootThreeCollisionBox4;
  @FXML private Polygon storageRootThreeCollisionBox5;
  @FXML private Polygon storageRootFourCollisionBox1;
  @FXML private Polygon storageRootFourCollisionBox2;
  @FXML private Polygon storageRootFourCollisionBox3;
  @FXML private Polygon storageRootFourCollisionBox4;
  @FXML private Polygon storageRootFourCollisionBox5;
  @FXML private Polygon storageRootFourCollisionBox6;
  @FXML private Polygon storageRootFourCollisionBox7;
  @FXML private Polygon storageRootFourCollisionBox8;
  @FXML private Polygon storageRootFourCollisionBox9;
  @FXML private Label counter;

  @FXML private ImageView controller;
  @FXML private ImageView windowController;
  @FXML private ImageView controller2;
  @FXML private ImageView thruster;
  @FXML private ImageView windowThruster;

  @FXML private Polygon processMachine;
  @FXML private Polygon bridgeDoor;
  @FXML private Polygon chest;
  @FXML private Polygon glass;
  @FXML private Polygon blueprintCollisionBox;

  @FXML private Label collectedTitle;
  @FXML private Label collectedLabel;
  @FXML private ImageView blueprintInfo;
  @FXML private ImageView windowInfo;
  @FXML private ImageView processMachineInfo;

  private ChatMessage gptMessage;
  private boolean passwordGenerate = false;

  public void goInside() {
    App.setUi(AppPanel.MAIN_ROOM);
  }

  public void goProgress() {
    SceneManager.setPrevious(AppPanel.STORAGE);
    App.setUi(AppPanel.PROGRESS);
  }

  /** Go to the chest if the password is generated or the first mission is completed. */
  public void goToChest() {
    if (passwordGenerate || !GameState.isFirstMissionCompleted) {
      App.setUi(AppPanel.CHEST);
      return;
    }
    if (!GameState.isControllerPuzzleShown) {

      GameState.generatePassWord();
      System.out.println(GameState.passWord);
      // SceneManager.showDialog("Info", "+", "What does this mean?");
      // when the user goes to the chest for the first time, the user sees the tree begin flashing
      // BRIGHTLY. At this time, a new gpt prompt will be created with a numerical puzzle and the
      // user
      // will be prompted with intro text while they wait for the tree to stop flashing.

      Task<Void> riddleSecondCall =
          new Task<Void>() {

            @Override
            protected Void call() throws Exception {

              System.out.println(
                  "this code is working"); // print statement to check if the code is working
              gptMessage =
                  ChatController.getResponse(
                      GptPromptEngineering.getControllerPuzzle(GameState.passWord),
                      ChatController.chatCompletionRequest);
              // Append the message to the chat text area and notebook
              Platform.runLater(
                  () ->
                      ((Label) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatLabel"))
                          .setText(gptMessage.getContent()));
              Platform.runLater(
                  () ->
                      ((TextArea) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatTextArea"))
                          .appendText(
                              "\n\n" + "Wise Ancient Tree: " + gptMessage.getContent() + "\n\n"));

              return null;
            }
          };

      // Start the thread
      Thread secondRiddleThread = new Thread(riddleSecondCall);
      secondRiddleThread.start();
      passwordGenerate = true;
      TreeAvatar.treeFlash.play();
      GameState.isControllerPuzzleShown = true;
      App.setUi(AppPanel.CHEST);
    } else {
      App.setUi(AppPanel.CHEST);
    }
  }

  /** This method execute features about collecting blueprint. */
  public void collectBlueprint() {
    activateCollectedInfoBluePrint();
    // Hide the blueprint image and collision box
    blueprint.setVisible(false);
    blueprintCollisionBox.setVisible(false);
    // Set the blueprint image in thruster room to visible
    SceneManager.getPanel(AppPanel.THRUSTER).lookup("#blueprintBackground").setVisible(true);
    // 1: purple    2: blue     3: red    4: green
    GameState.missionManager.getMission(MISSION.THRUSTER).increaseStage();
    System.out.println(GameState.missionManager.getMission(MISSION.THRUSTER).getStage());
    GameState.progressBarGroup.updateProgressTwo(MISSION.THRUSTER);
  }

  /**
   * Melts sand in the furnace when the mission is the window. If the inventory contains sand, the
   * mission stage is increased, progress bar is updated, process machine is hidden, and glass is
   * shown. If the inventory does not contain sand, information about the process machine is
   * displayed.
   */
  public void meltSand() {
    // If the sand bucket is in the inventory
    if (GameState.inventory.contains(2)) {
      // Increase the stage of window mission
      GameState.missionManager.getMission(MISSION.WINDOW).increaseStage();
      GameState.progressBarGroup.updateProgressOne(MISSION.WINDOW);
      // Deactivate the collision box for process machine
      processMachine.setVisible(false);
      processMachine.setDisable(true);
      showGlass();
    } else {
      // If the sand bucket is not in the inventory, show the information about process machine
      collectedTitle.setText("Process Machine");
      collectedLabel.setText("High temperature processing machine");
      collectedTitle.setVisible(true);
      collectedLabel.setVisible(true);
      processMachineInfo.setVisible(true);
    }
  }

  /** Show the glass image according to the mission selected. */
  private void showGlass() {
    if (GameState.missionList.contains(3)) {
      // If the second mission is controller mission
      windowController.setVisible(true);
    } else {
      // If the second mission is thruster mission
      windowThruster.setVisible(true);
    }
    // Activate the collision box for glass
    glass.setVisible(true);
    glass.setDisable(false);
  }

  /** This method execute features about collecting glass. */
  public void collectGlass() {
    activateCollectedInfoWindow();
    GameState.inventory.add(3);
    glass.setVisible(false);
    glass.setDisable(true);
    // Show the glass collected image
    if (GameState.missionList.contains(3)) {
      // If the second mission is controller mission
      windowController.setVisible(false);
    } else {
      // If the second mission is thruster mission
      windowThruster.setVisible(false);
    }
  }

  public void activateProgressGlow() {
    progressButton.setEffect(GameState.glowBright);
  }

  public void deactivateProgressGlow() {
    progressButton.setEffect(GameState.glowDim);
  }

  /** This method Activate the glow effect of the door. */
  public void activateDoorGlow() {
    storageDoor.setEffect(GameState.glowBright);
    storageDoor.setCursor(Cursor.HAND);
    bridgeDoor.setOpacity(1);
    bridgeDoor.setCursor(Cursor.HAND);
  }

  public void deactivateDoorGlow() {
    storageDoor.setEffect(GameState.glowDim);
    bridgeDoor.setOpacity(0);
  }

  public void activateHiddenChestGlow() {
    hiddenChestImage.setEffect(GameState.glowBright);
    hiddenChestImage.setCursor(Cursor.HAND);
  }

  public void deactivateHiddenChestGlow() {
    hiddenChestImage.setEffect(GameState.glowDim);
  }

  public void activateChestGlow() {
    chest.setOpacity(1);
    chest.setCursor(Cursor.HAND);
  }

  public void deactivateChestGlow() {
    chest.setOpacity(0);
  }

  public void activateProcessMachineGlow() {
    processMachine.setOpacity(1);
    processMachine.setCursor(Cursor.HAND);
  }

  public void deactivateProcessMachineGlow() {
    processMachine.setOpacity(0);
  }

  public void activateGlassGlow() {
    glass.setOpacity(1);
    glass.setCursor(Cursor.HAND);
  }

  public void deactivateGlassGlow() {
    glass.setOpacity(0);
  }

  public void activateBlueprintGlow() {
    blueprintCollisionBox.setOpacity(1);
    blueprintCollisionBox.setCursor(Cursor.HAND);
  }

  public void deactivateBlueprintGlow() {
    blueprintCollisionBox.setOpacity(0);
  }

  /** This method set the panel to Chatroom. */
  public void goChat() {
    TreeAvatar.treeFlash.pause();
    TreeAvatar.deactivateTreeGlow();
    SceneManager.setPrevious(AppPanel.STORAGE);

    App.setUi(AppPanel.CHAT);
  }

  public void miniTreeGlow() {
    miniTree.setEffect(GameState.glowBright);
  }

  public void miniTreeDim() {
    miniTree.setEffect(GameState.glowDim);
  }

  /** This method opens the info panel for blurprint. */
  private void activateCollectedInfoBluePrint() {
    collectedTitle.setText("Blueprint Collected");
    collectedTitle.setVisible(true);
    blueprintInfo.setVisible(true);
  }

  /** This method opens the info panel for window. */
  private void activateCollectedInfoWindow() {
    collectedTitle.setText("Window Collected");
    collectedTitle.setVisible(true);
    windowInfo.setVisible(true);
  }

  /** This method closes all info panel. */
  public void exitInfo() {
    collectedTitle.setVisible(false);
    collectedLabel.setVisible(false);
    blueprintInfo.setVisible(false);
    windowInfo.setVisible(false);
    processMachineInfo.setVisible(false);
  }

  public void activateRootOneGlow() { // activate root one glow
    // box 1
    storageRootOneCollisionBox1.setOpacity(
        1); // sets the opacity of the root one collision box to 1
    storageRootOneCollisionBox1.setCursor(
        Cursor.HAND); // sets the cursor of the root one collision box to hand
    // box 2
    storageRootOneCollisionBox2.setOpacity(1);
    storageRootOneCollisionBox2.setCursor(Cursor.HAND);
    // box 3
    storageRootOneCollisionBox3.setOpacity(1);
    storageRootOneCollisionBox3.setCursor(Cursor.HAND);
    // box 4
    storageRootOneCollisionBox4.setOpacity(1);
    storageRootOneCollisionBox4.setCursor(Cursor.HAND);
  }

  /** Deactivate glow effect of root one's collision box. */
  public void deactivateRootOneGlow() {
    storageRootOneCollisionBox1.setOpacity(0);
    storageRootOneCollisionBox2.setOpacity(0);
    storageRootOneCollisionBox3.setOpacity(0);
    storageRootOneCollisionBox4.setOpacity(0);
  }

  /** Activates the glow effect for the root two collision boxes and sets their cursor to hand. */
  public void activateRootTwoGlow() { // activate root two glow
    // box 1
    storageRootTwoCollisionBox1.setOpacity(
        1); // sets the opacity of the root two collision box to 1
    storageRootTwoCollisionBox1.setCursor(
        Cursor.HAND); // sets the cursor of the root two collision box to hand
    // box 2
    storageRootTwoCollisionBox2.setOpacity(1);
    storageRootTwoCollisionBox2.setCursor(Cursor.HAND);
    // box 3
    storageRootTwoCollisionBox3.setOpacity(1);
    storageRootTwoCollisionBox3.setCursor(Cursor.HAND);
    // box 4
    storageRootTwoCollisionBox4.setOpacity(1);
    storageRootTwoCollisionBox4.setCursor(Cursor.HAND);
  }

  /** Deactivate glow effect of root two's collision box. */
  public void deactivateRootTwoGlow() {
    storageRootTwoCollisionBox1.setOpacity(0);
    storageRootTwoCollisionBox2.setOpacity(0);
    storageRootTwoCollisionBox3.setOpacity(0);
    storageRootTwoCollisionBox4.setOpacity(0);
  }

  /** Activates the glow effect for the root three collision boxes and sets their cursor to hand. */
  public void activateRootThreeGlow() { // activate root three glow
    // box 1
    storageRootThreeCollisionBox1.setOpacity(1);
    storageRootThreeCollisionBox1.setCursor(Cursor.HAND);
    // box 2
    storageRootThreeCollisionBox2.setOpacity(1);
    storageRootThreeCollisionBox2.setCursor(Cursor.HAND);
    // box 3
    storageRootThreeCollisionBox3.setOpacity(1);
    storageRootThreeCollisionBox3.setCursor(Cursor.HAND);
    // box 4
    storageRootThreeCollisionBox4.setOpacity(1);
    storageRootThreeCollisionBox4.setCursor(Cursor.HAND);
    // box 5
    storageRootThreeCollisionBox5.setOpacity(1);
    storageRootThreeCollisionBox5.setCursor(Cursor.HAND);
  }

  /** Deactivate glow effect of root three's collision box. */
  public void deactivateRootThreeGlow() {
    storageRootThreeCollisionBox1.setOpacity(0);
    storageRootThreeCollisionBox2.setOpacity(0);
    storageRootThreeCollisionBox3.setOpacity(0);
    storageRootThreeCollisionBox4.setOpacity(0);
    storageRootThreeCollisionBox5.setOpacity(0);
  }

  /** Activate the glow effect on the collision box of root four. */
  public void activateRootFourGlow() {
    // activate root four glow
    // box 1
    storageRootFourCollisionBox1.setOpacity(1);
    storageRootFourCollisionBox1.setCursor(Cursor.HAND);
    // box 2
    storageRootFourCollisionBox2.setOpacity(1);
    storageRootFourCollisionBox2.setCursor(Cursor.HAND);
    // box 3
    storageRootFourCollisionBox3.setOpacity(1);
    storageRootFourCollisionBox3.setCursor(Cursor.HAND);
    // box 4
    storageRootFourCollisionBox4.setOpacity(1);
    storageRootFourCollisionBox4.setCursor(Cursor.HAND);
    // box 5
    storageRootFourCollisionBox5.setOpacity(1);
    storageRootFourCollisionBox5.setCursor(Cursor.HAND);
    // box 6
    storageRootFourCollisionBox6.setOpacity(1);
    storageRootFourCollisionBox6.setCursor(Cursor.HAND);
    // box 7
    storageRootFourCollisionBox7.setOpacity(1);
    storageRootFourCollisionBox7.setCursor(Cursor.HAND);
    // box 8
    storageRootFourCollisionBox8.setOpacity(1);
    storageRootFourCollisionBox8.setCursor(Cursor.HAND);
    // box 9
    storageRootFourCollisionBox9.setOpacity(1);
    storageRootFourCollisionBox9.setCursor(Cursor.HAND);
  }

  /**
   * Deactivates the glow effect on the root four collision boxes. This method sets the opacity of
   * all nine collision boxes to 0, effectively removing the glow effect.
   */
  public void deactivateRootFourGlow() {
    // deactivate root four glow
    // box 1, 2, and 3
    storageRootFourCollisionBox1.setOpacity(0);
    storageRootFourCollisionBox2.setOpacity(0);
    storageRootFourCollisionBox3.setOpacity(0);
    // box 4, 5, and 6
    storageRootFourCollisionBox4.setOpacity(0);
    storageRootFourCollisionBox5.setOpacity(0);
    storageRootFourCollisionBox6.setOpacity(0);
    // box 7, 8, and 9
    storageRootFourCollisionBox7.setOpacity(0);
    storageRootFourCollisionBox8.setOpacity(0);
    storageRootFourCollisionBox9.setOpacity(0);
  }
}
