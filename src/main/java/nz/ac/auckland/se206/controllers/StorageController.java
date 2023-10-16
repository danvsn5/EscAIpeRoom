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
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

public class StorageController {

  @FXML private ImageView progressButton;
  @FXML private ImageView storageDoor;
  @FXML private ImageView hiddenChestImage;
  // @FXML private ImageView chest;
  @FXML private ImageView blueprint;
  @FXML private ImageView miniTree;
  @FXML private ImageView root1;
  @FXML private ImageView root2;
  @FXML private ImageView root3;
  @FXML private ImageView root4;
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

              System.out.println("this code is working");
              gptMessage =
                  runGpt(
                      new ChatMessage(
                          "user", GptPromptEngineering.getControllerPuzzle(GameState.passWord)));
              Platform.runLater(() -> appendChatMessage(gptMessage));

              System.out.println(gptMessage.getContent());

              return null;
            }
          };

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

  public void meltSand() { // furnace button when the mission is the window.
    if (GameState.inventory.contains(2)) { // checks if the inventory contains sand
      GameState.missionManager.getMission(MISSION.WINDOW).increaseStage();
      GameState.progressBarGroup.updateProgressOne(MISSION.WINDOW);
      processMachine.setVisible(false);
      processMachine.setDisable(true);
      showGlass();
    } else {
      collectedTitle.setText("Process Machine");
      collectedLabel.setText("High tech process machine, can make ingredients into product");
      collectedTitle.setVisible(true);
      collectedLabel.setVisible(true);
      processMachineInfo.setVisible(true);
    }
  }

  /** Show the glass image according to the mission selected */
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

  private void activateCollectedInfoBluePrint() {
    collectedTitle.setText("BluePint Collected");
    collectedTitle.setVisible(true);
    blueprintInfo.setVisible(true);
  }

  private void activateCollectedInfoWindow() {
    collectedTitle.setText("Window Collected");
    collectedTitle.setVisible(true);
    windowInfo.setVisible(true);
  }

  public void exitInfo() {
    collectedTitle.setVisible(false);
    collectedLabel.setVisible(false);
    blueprintInfo.setVisible(false);
    windowInfo.setVisible(false);
    processMachineInfo.setVisible(false);
  }

  public void activateRootOneGlow() {

    rootOneCollisionBox1.setOpacity(1);
    rootOneCollisionBox1.setCursor(Cursor.HAND);
    rootOneCollisionBox2.setOpacity(1);
    rootOneCollisionBox2.setCursor(Cursor.HAND);
    rootOneCollisionBox3.setOpacity(1);
    rootOneCollisionBox3.setCursor(Cursor.HAND);
    rootOneCollisionBox4.setOpacity(1);
    rootOneCollisionBox4.setCursor(Cursor.HAND);
  }

  public void deactivateRootOneGlow() {
    rootOneCollisionBox1.setOpacity(0);
    rootOneCollisionBox2.setOpacity(0);
    rootOneCollisionBox3.setOpacity(0);
    rootOneCollisionBox4.setOpacity(0);
  }

  public void activateRootTwoGlow() {
    rootTwoCollisionBox1.setOpacity(1);
    rootTwoCollisionBox1.setCursor(Cursor.HAND);
    rootTwoCollisionBox2.setOpacity(1);
    rootTwoCollisionBox2.setCursor(Cursor.HAND);
    rootTwoCollisionBox3.setOpacity(1);
    rootTwoCollisionBox3.setCursor(Cursor.HAND);
    rootTwoCollisionBox4.setOpacity(1);
    rootTwoCollisionBox4.setCursor(Cursor.HAND);
  }

  public void deactivateRootTwoGlow() {
    rootTwoCollisionBox1.setOpacity(0);
    rootTwoCollisionBox2.setOpacity(0);
    rootTwoCollisionBox3.setOpacity(0);
    rootTwoCollisionBox4.setOpacity(0);
  }

  public void activateRootThreeGlow() {
    rootThreeCollisionBox1.setOpacity(1);
    rootThreeCollisionBox1.setCursor(Cursor.HAND);
    rootThreeCollisionBox2.setOpacity(1);
    rootThreeCollisionBox2.setCursor(Cursor.HAND);
    rootThreeCollisionBox3.setOpacity(1);
    rootThreeCollisionBox3.setCursor(Cursor.HAND);
    rootThreeCollisionBox4.setOpacity(1);
    rootThreeCollisionBox4.setCursor(Cursor.HAND);
    rootThreeCollisionBox5.setOpacity(1);
    rootThreeCollisionBox5.setCursor(Cursor.HAND);
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
    rootFourCollisionBox1.setCursor(Cursor.HAND);
    rootFourCollisionBox2.setOpacity(1);
    rootFourCollisionBox2.setCursor(Cursor.HAND);
    rootFourCollisionBox3.setOpacity(1);
    rootFourCollisionBox3.setCursor(Cursor.HAND);
    rootFourCollisionBox4.setOpacity(1);
    rootFourCollisionBox4.setCursor(Cursor.HAND);
    rootFourCollisionBox5.setOpacity(1);
    rootFourCollisionBox5.setCursor(Cursor.HAND);
    rootFourCollisionBox6.setOpacity(1);
    rootFourCollisionBox6.setCursor(Cursor.HAND);
    rootFourCollisionBox7.setOpacity(1);
    rootFourCollisionBox7.setCursor(Cursor.HAND);
    rootFourCollisionBox8.setOpacity(1);
    rootFourCollisionBox8.setCursor(Cursor.HAND);
    rootFourCollisionBox9.setOpacity(1);
    rootFourCollisionBox9.setCursor(Cursor.HAND);
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

  /* ======================================= GPT Helper Methods ======================================= */
  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
    ChatController.chatCompletionRequest.addMessage(msg);
    try {
      ChatCompletionResult chatCompletionResult = ChatController.chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      ChatController.chatCompletionRequest.addMessage(result.getChatMessage());
      result.getChatMessage().setRole("Wise Mystical Tree");
      result.getChatMessage().setRole("assistant");
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      ChatMessage error = new ChatMessage(null, null);

      error.setRole("Wise Mystical Tree");

      error.setContent(
          "Sorry, there was a problem generating a response. Please try restarting the"
              + " application.");
      appendChatMessage(error);
      e.printStackTrace();
      return null;
    }
  }

  private void appendChatMessage(ChatMessage msg) {
    // chatTextArea.appendText(msg.getRole() + ": " + msg.getContent() + "\n\n");
    ((TextArea) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatTextArea"))
        .appendText("\n\n" + "Wise Ancient Tree: " + msg.getContent() + "\n\n");
    ((Label) SceneManager.getPanel(AppPanel.CHAT).lookup("#chatLabel")).setText(msg.getContent());
  }
}
