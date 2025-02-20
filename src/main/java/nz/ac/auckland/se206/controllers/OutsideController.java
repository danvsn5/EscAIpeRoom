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
import nz.ac.auckland.se206.MissionManager.MissionType;
import nz.ac.auckland.se206.RootBorder;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.TreeAvatar;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/**
 * The OutsideController class is responsible for controlling 
 * the behavior of the outside panel in the game.
 * It contains methods for navigating to other panels, generating puzzles, 
 * and displaying inventory items.
 */
public class OutsideController {
  public static int thrusterPuzzleGenerate = 0;

  @FXML private ImageView outsideImage;
  @FXML private ImageView outsideBrokenImage;
  @FXML private ImageView returnShip;
  @FXML private ImageView progressButton;
  @FXML private ImageView root1;
  @FXML private ImageView root2;
  @FXML private ImageView root3;
  @FXML private ImageView root4;
  @FXML private ImageView miniTree;
  @FXML private ImageView ship;
  @FXML private ImageView sandInfo;
  @FXML private Label collectedLabel;
  @FXML private Label collectedTitle;
  @FXML private Label counter;
  @FXML private Polygon wiseTree;
  @FXML private Polygon thruster1;
  @FXML private Polygon thruster2;
  @FXML private Polygon shipDoor1;
  @FXML private Polygon shipDoor2;
  @FXML private Polygon sand;

  private ChatMessage gptMessage;

  // displays counter on panel and constantly checks if the riddle has been solved. If riddle was
  // solved correctly, and sand is currently NOT in the inventory, then the sand appears inside the
  // panel.
  public void goProgress() {
    SceneManager.setPrevious(AppPanel.OUTSIDE);
    App.setUi(AppPanel.PROGRESS);
  }

  public void goToShip() {
    App.setUi(AppPanel.MAIN_ROOM);
  }

  public void outsideReturn() {
    SceneManager.setPrevious(AppPanel.MAIN_ROOM);
    App.setUi(AppPanel.MAIN_ROOM);
  }

  /**
   * This method sets the panel to thruster room.
   *
   * @throws ApiProxyException If there is an api error.
   */
  public void goThruster() {
    // If the thruster mission is selected
    if (GameState.missionList.contains(4)) {
      // If the thruster puzzle is not generated but the first mission is completed, generate the
      // message
      if (thrusterPuzzleGenerate == 0 && GameState.isFirstMissionCompleted == true) {
        // This task generate the riddle for thruster mission
        Task<Void> riddleSecondCall =
            new Task<Void>() {

              @Override
              protected Void call() throws Exception {

                System.out.println("this code is working");

                // switch case from 1 to 4 based on the variable GameState.randomColourNumber
                // 1: purple    2: blue     3: red    4: green
                switch (GameState.randomColorNumber) {
                  case 1:
                    gptMessage =
                        ChatController.getResponse(
                            GptPromptEngineering.getThrusterPuzzle("purple"),
                            ChatController.chatCompletionRequest);
                    break;
                  case 2:
                    gptMessage =
                        ChatController.getResponse(
                            GptPromptEngineering.getThrusterPuzzle("red"),
                            ChatController.chatCompletionRequest);
                    break;
                  case 3:
                    gptMessage =
                        ChatController.getResponse(
                            GptPromptEngineering.getThrusterPuzzle("blue"),
                            ChatController.chatCompletionRequest);
                    break;
                  case 4:
                    gptMessage =
                        ChatController.getResponse(
                            GptPromptEngineering.getThrusterPuzzle("green"),
                            ChatController.chatCompletionRequest);
                    break;
                }
                thrusterPuzzleGenerate = 1;
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

                System.out.println(gptMessage.getContent());

                return null;
              }
            };

        // Start the thread and start root flashing
        Thread secondRiddleThread = new Thread(riddleSecondCall);
        secondRiddleThread.start();
        TreeAvatar.treeFlash.play();
      }
      // Set the panel to Thruster
      App.setUi(AppPanel.THRUSTER);
    }
  }

  /**
   * When the first message is not shown, this method record that first message is shown and set.
   * panel to Chat.
   *
   * @throws ApiProxyException If there is an api error.
   */
  public void openRiddle() throws ApiProxyException {
    // Set the previous panel to Outside
    SceneManager.setPrevious(AppPanel.OUTSIDE);
    // Stop root flashing
    TreeAvatar.treeFlash.pause();
    TreeAvatar.deactivateTreeGlow();
    // If the first message is not shown, record that first message is shown and set panel to Chat
    if (ChatController.seenFirstMessage == 0) {
      RootBorder.deactivateAllCollisionBox();
      RootBorder.activateAllCollisionBox();
      ChatController.seenFirstMessage = 1;
    }

    App.setUi(AppPanel.CHAT);
  }

  public void activateProgressGlow() {
    progressButton.setEffect(GameState.glowBright);
  }

  public void deactivateProgressGlow() {
    progressButton.setEffect(GameState.glowDim);
  }

  /** This method sets the panel to chatroom. */
  public void goChat() {
    TreeAvatar.treeFlash.pause();
    TreeAvatar.deactivateTreeGlow();
    App.setUi(AppPanel.CHAT);
    SceneManager.setPrevious(AppPanel.OUTSIDE);
  }

  public void miniTreeGlow() {
    miniTree.setEffect(GameState.glowBright);
  }

  public void miniTreeDim() {
    miniTree.setEffect(GameState.glowDim);
  }

  /** This method activates the glow of ship's collision box. */
  public void activateShipGlow() {
    ship.setEffect(GameState.glowBright);
    ship.setCursor(Cursor.HAND);
    shipDoor1.setOpacity(1);
    shipDoor1.setCursor(Cursor.HAND);
    shipDoor2.setOpacity(1);
    shipDoor2.setCursor(Cursor.HAND);
  }

  /** This method deactivate the glow of ship door. */
  public void deactivateShipGlow() {
    ship.setEffect(GameState.glowDim);
    shipDoor1.setOpacity(0);
    shipDoor2.setOpacity(0);
  }

  public void activateTreeGlow() {
    wiseTree.setOpacity(1);
    wiseTree.setCursor(Cursor.HAND);
  }

  public void deactivateTreeGlow() {
    wiseTree.setOpacity(0);
  }

  /** This method checks for thruster mission and activate the glow of thruster collision box. */
  public void activateThrusterGlow() {
    if (GameState.missionList.contains(4)) {
      thruster1.setOpacity(1);
      thruster1.setCursor(Cursor.HAND);
      thruster2.setOpacity(1);
      thruster2.setCursor(Cursor.HAND);
    }
  }

  /** This class de activate the glow of thruster's collision box. */
  public void deactivateThrusterGlow() {
    if (GameState.missionList.contains(4)) {
      thruster1.setOpacity(0);
      thruster2.setOpacity(0);
    }
  }

  /** This method shows the sand when the bucket is collected. */
  public void activateSandGlow() {
    // If the bucket is collected and the sand is not collected, show the sand
    if (GameState.isBucketCollected && !GameState.isSandCollected) {
      sand.setDisable(false);
      sand.setOpacity(1);
      sand.setCursor(Cursor.HAND);
    }
  }

  public void deactivateSandGlow() {
    sand.setOpacity(0);
  }

  /** This method collects sand and run all related code. */
  public void collectSand() {
    // If the bucket is collected and the sand is not collected, add sand to inventory
    if (GameState.isBucketCollected && !GameState.isSandCollected) {
      // Add sand to inventory and increase stage of window mission
      GameState.inventory.add(2);
      GameState.missionManager.getMission(MissionType.WINDOW).increaseStage();
      GameState.progressBarGroup.updateProgressOne(MissionType.WINDOW);
      // Show the sand collected message
      collectedTitle.setText("Sand Collected");
      collectedLabel.setText("A bucket full of sand which may be processed into glass");
      collectedLabel.setVisible(true);
      collectedTitle.setVisible(true);
      sandInfo.setVisible(true);
      sand.setDisable(true);
      sand.setVisible(false);
      // Record that sand is collected
      System.out.println("Sand collected");
      GameState.isSandCollected = true;
    }
  }

  /** This method closes all info panel in this page. */
  public void exitInfo() {
    collectedLabel.setVisible(false);
    sandInfo.setVisible(false);
    collectedTitle.setVisible(false);
  }
}
