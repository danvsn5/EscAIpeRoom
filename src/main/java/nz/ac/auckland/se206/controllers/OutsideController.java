package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionManager.MISSION;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.TreeAvatar;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class OutsideController {
  @FXML private ImageView returnShip;
  @FXML private Circle tech;
  @FXML private Circle wiseTree;
  @FXML private Label counter;
  @FXML private Label treeLabel;
  @FXML private Label shipLabel;
  @FXML private Label techLabel;
  @FXML private ImageView progressButton;
  @FXML private ImageView rootInitial;
  @FXML private ImageView rootOne;
  @FXML private ImageView rootTwo;
  @FXML private ImageView rootThree;
  @FXML private ImageView crashLand;
  @FXML private ImageView thruster;
  @FXML private ImageView miniTree;
  @FXML private Rectangle sand;
  @FXML private Label isSandCollected;

  @FXML private Label fuelCharged;

  public void initialize() {}

  // displays counter on panel and constantly checks if the riddle has been solved. If riddle was
  // solved correctly, and sand is currently NOT in the inventory, then the sand appears inside the
  // panel.
  public void goProgress() {
    App.setUi(AppPanel.PROGRESS);
  }

  public void outsideReturn() {
    SceneManager.setPrevious(AppPanel.MAIN_ROOM);
    App.setUi(AppPanel.MAIN_ROOM);
  }

  public void goThruster() {
    if (GameState.inventory.contains(8)) {
      GameState.missionManager.getMission(MISSION.FUEL).increaseStage();
      GameState.progressBarGroup.updateProgressOne(MISSION.FUEL);
      System.out.println("Fuel Mission Complete");
      GameState.isFirstMissionCompleted = true;
      GameState.inventory.remove(GameState.inventory.indexOf(8));
      fuelCharged.setVisible(true);
    } else {
      App.setUi(AppPanel.THRUSTER);
    }
  }
  public void goCrashLand() {
    App.setUi(AppPanel.CRASHLAND);
  }

  public void collectTech() {
    GameState.inventory.add(1);
    tech.setVisible(false);
  }

  // techLabel methods make the label appear and disappear depending on whether or not the mouse is
  // hovering over the object
  public void techLabelOn() {
    techLabel.setVisible(true);
  }

  public void techLabelOff() {
    techLabel.setVisible(false);
  }

  public void collectSand() {
    System.out.println("Sand to be collected");
    if (GameState.firstRiddleSolved && GameState.missionList.contains(1)) {
      GameState.missionManager.getMission(MISSION.WINDOW).increaseStage();
      GameState.progressBarGroup.updateProgressOne(MISSION.WINDOW);
      System.out.println("Window Mission Complete");
      System.out.println(GameState.missionManager.getMission(MISSION.WINDOW).getStage());

      GameState.inventory.add(2);
      sand.setDisable(true);
      isSandCollected.setVisible(true);
    } else {
      System.out.println("You need to solve the riddle first!");
    }
  }

  // there are two types of methods below: Light and Dark/Normal. On hover over with mouse, Light
  // method is invoked: the color of the selected object becomes lighter and a label becomes
  // visible, indicating it is
  // clickble. Once mouse is moved from object, color returns to original and the label is made
  // invisible with Dark/Normal method
  // invokation.

  public void treeLight() {
    wiseTree.setFill(Color.valueOf("864310"));
    treeLabel.setVisible(true);
  }

  public void treeNormal() {
    wiseTree.setFill(Color.valueOf("6f3506"));
    treeLabel.setVisible(false);
  }

  public void sandLight() {
    sand.setFill(Color.valueOf("fffccc"));
  }

  public void sandNormal() {
    sand.setFill(Color.valueOf("ffba1f"));
  }

  public void openRiddle() throws ApiProxyException {
    SceneManager.setPrevious(AppPanel.MAIN_ROOM);
    TreeAvatar.treeFlash.pause();
    TreeAvatar.deactivateTreeGlow();
    TreeAvatar.setTreeVisible();

    App.setUi(AppPanel.CHAT);
  }

  public void activateProgressGlow() {
    progressButton.setEffect(GameState.glowBright);
  }

  public void deactivateProgressGlow() {
    progressButton.setEffect(GameState.glowDim);
  }

  public void activateCrashLandGlow() {
    crashLand.setEffect(GameState.glowBright);
  }

  public void deactivateCrashLandGlow() {
    crashLand.setEffect(GameState.glowDim);
  }

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
}
