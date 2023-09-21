package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
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
  @FXML private Label counter;
  @FXML private Polygon wiseTree;
  @FXML private Polygon thruster;
  @FXML private ImageView progressButton;
  @FXML private ImageView rootInitial;
  @FXML private ImageView rootOne;
  @FXML private ImageView rootTwo;
  @FXML private ImageView rootThree;
  @FXML private ImageView miniTree;
  @FXML private ImageView treeImage;
  @FXML private ImageView ship;
  @FXML private ImageView thrusterImage;
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

  public void goToShip() {
    App.setUi(AppPanel.MAIN_ROOM);
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

  public void sandLight() {
    sand.setFill(Color.valueOf("fffccc"));
  }

  public void sandNormal() {
    sand.setFill(Color.valueOf("ffba1f"));
  }

  public void openRiddle() throws ApiProxyException {
    SceneManager.setPrevious(AppPanel.OUTSIDE);
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

  public void activateShipGlow() {
    ship.setEffect(GameState.glowBright);
  }

  public void deactivateShipGlow() {
    ship.setEffect(GameState.glowDim);
  }

  public void activateTreeGlow() {
    treeImage.setEffect(GameState.glowBright);
  }

  public void deactivateTreeGlow() {
    treeImage.setEffect(GameState.glowDim);
  }

  public void activateThrusterGlow() {
    thrusterImage.setEffect(GameState.glowBright);
  }

  public void deactivateThrusterGlow() {
    thrusterImage.setEffect(GameState.glowDim);
  }
}
