package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionManager.MISSION;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.TreeAvatar;

public class StorageController {

  @FXML private Rectangle hiddenChest;
  @FXML private Polygon furnace;
  @FXML private ImageView progressButton;
  @FXML private ImageView storageDoor;
  @FXML private ImageView hiddenChestImage;
  @FXML private ImageView chest;
  @FXML private ImageView blueprint;
  @FXML private ImageView furnaceImage;
  @FXML private ImageView miniTree;
  @FXML private ImageView rootInitial;
  @FXML private ImageView rootOne;
  @FXML private ImageView rootTwo;
  @FXML private ImageView rootThree;
  @FXML private Label counter;

  public void goInside() {
    App.setUi(AppPanel.MAIN_ROOM);
  }

  public void goProgress() {
    SceneManager.setPrevious(AppPanel.STORAGE);
    App.setUi(AppPanel.PROGRESS);
  }

  public void findChest() {
    hiddenChest.setDisable(true);
    chest.setDisable(false);
    chest.setOpacity(1);
    SceneManager.showDialog("Info", "Chest found", "A wooden chest with a digital lock on it");
  }

  public void goToChest() {
    App.setUi(AppPanel.CHEST);
  }

  public void collectBlueprint() {
    blueprint.setVisible(false);
    SceneManager.getPanel(AppPanel.THRUSTER).lookup("#blueprint").setVisible(true);
    GameState.missionManager.getMission(MISSION.THRUSTER).increaseStage();
    GameState.progressBarGroup.updateProgressTwo(MISSION.THRUSTER);
  }

  public void meltSand() {
    if (GameState.inventory.contains(2)) {
      GameState.missionManager.getMission(MISSION.WINDOW).increaseStage();
      GameState.progressBarGroup.updateProgressOne(MISSION.WINDOW);
      GameState.inventory.add(3);
      furnace.setDisable(true);
      SceneManager.showDialog("Info", "Glass collected", "A well-made window");
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
  }

  public void deactivateDoorGlow() {
    storageDoor.setEffect(GameState.glowDim);
  }

  public void activateHiddenChestGlow() {
    hiddenChestImage.setEffect(GameState.glowBright);
  }

  public void deactivateHiddenChestGlow() {
    hiddenChestImage.setEffect(GameState.glowDim);
  }

  public void activateChestGlow() {
    chest.setEffect(GameState.glowBright);
  }

  public void deactivateChestGlow() {
    chest.setEffect(GameState.glowDim);
  }

  public void activateFurnaceGlow() {
    furnaceImage.setEffect(GameState.glowBright);
  }

  public void deactivateFurnaceGlow() {
    furnaceImage.setEffect(GameState.glowDim);
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
}
