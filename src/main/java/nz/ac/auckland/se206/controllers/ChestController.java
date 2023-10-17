package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionManager.MissionType;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.TreeAvatar;

/**
 * The controller for the chest mini-game. Allows the player to input a three-digit password and
 * unlock the chest to collect a spare part for the controller. Also provides navigation buttons to
 * go back to the storage panel and progress panel, as well as a button to go to the chat room.
 */
public class ChestController {
  @FXML private Button backButton;
  @FXML private Button enterButton;
  @FXML private Label firstDigit;
  @FXML private Label secondDigit;
  @FXML private Label thirdDigit;
  @FXML private Label counter;
  @FXML private Label firstNumebr;
  @FXML private Label secondNumber;
  @FXML private Rectangle firstDigitHighlight;
  @FXML private Rectangle secondDigitHighlight;
  @FXML private Rectangle thirdDigitHighlight;
  @FXML private ImageView progressButton;
  @FXML private ImageView miniTree;
  @FXML private ImageView chestWrongImage;
  @FXML private ImageView chestOpenImage;
  @FXML private ImageView controlPanelInfo;
  @FXML private Label collectedLabel;
  @FXML private Label collectedTitle;

  @FXML private ImageView chestRoot;
  @FXML private Polygon rootCollisionBox1;
  @FXML private Polygon rootCollisionBox2;
  @FXML private Polygon rootCollisionBox3;

  private int firstDigitNum = 0;
  private int secondDigitNum = 0;
  private int thirdDigitNum = 0;

  /** This method is involked when backButton is clicked, set the panel to storage room. */
  @FXML
  private void onGoBack() {
    App.setUi(AppPanel.STORAGE);
  }

  public void goProgress() {
    SceneManager.setPrevious(AppPanel.CHEST);
    App.setUi(AppPanel.PROGRESS);
  }

  /** This method increase the first digit by 1. */
  public void firstDigitUp() {
    chestWrongImage.setVisible(false);
    firstDigitNum++;
    if (firstDigitNum >= 10) {
      firstDigitNum = 0;
    }
    firstDigit.setText(Integer.toString(firstDigitNum));
  }

  /** This method increase the second digit by 1. */
  public void secondDigitUp() {
    chestWrongImage.setVisible(false);
    secondDigitNum++;
    if (secondDigitNum >= 10) {
      secondDigitNum = 0;
    }
    secondDigit.setText(Integer.toString(secondDigitNum));
  }

  /** This method increase the third digit by 1. */
  public void thirdDigitUp() {
    chestWrongImage.setVisible(false);
    thirdDigitNum++;
    if (thirdDigitNum >= 10) {
      thirdDigitNum = 0;
    }
    thirdDigit.setText(Integer.toString(thirdDigitNum));
  }

  /**
   * This method is invoked when confirm button is clicked, checks if the player's input number is
   * correct or not.
   */
  @FXML
  private void onCheck() {
    // Get the user's input password
    int password = firstDigitNum * 100 + secondDigitNum * 10 + thirdDigitNum;
    if (password == GameState.passWord) {
      // If the player is correct, update the progress and disable the lock
      GameState.missionManager.getMission(MissionType.CONTROLLER).increaseStage();
      GameState.progressBarGroup.updateProgressTwo(MissionType.CONTROLLER);

      collectedTitle.setText("Control panel");
      collectedLabel.setText("Spare conroller parts");
      collectedTitle.setVisible(true);
      collectedLabel.setVisible(true);
      controlPanelInfo.setVisible(true);
      disableLock();
    } else {
      chestWrongImage.setVisible(true);
    }
  }

  /**
   * Disables the lock of the chest by hiding the enter button and the highlight of the three
   * digits, disabling the collision box of the chest, and activating the chest image in the storage
   * panel. Also activates the chest open image.
   */
  public void disableLock() {
    // Disable the enter button and highlight of three digits
    enterButton.setOpacity(0);
    enterButton.setDisable(true);
    firstDigitHighlight.setDisable(true);
    secondDigitHighlight.setDisable(true);
    thirdDigitHighlight.setDisable(true);
    // Disable the chest collision box and activate the chest image in storage panel
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#chest").setDisable(false);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#chest").setVisible(false);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#controller2").setVisible(true);
    // Activate the chest open image
    chestOpenImage.setOpacity(1);
  }

  public void activateProgressGlow() {
    progressButton.setEffect(GameState.glowBright);
  }

  public void deactivateProgressGlow() {
    progressButton.setEffect(GameState.glowDim);
  }

  public void firstDigitGlow() {
    firstDigitHighlight.setOpacity(1);
    firstDigitHighlight.setCursor(Cursor.HAND);
  }

  public void darkeningFirstDigit() {
    firstDigitHighlight.setOpacity(0);
  }

  public void secondDigitGlow() {
    secondDigitHighlight.setOpacity(1);
    secondDigitHighlight.setCursor(Cursor.HAND);
  }

  public void secondDigitDark() {
    secondDigitHighlight.setOpacity(0);
  }

  public void thirdDigitGlow() {
    thirdDigitHighlight.setOpacity(1);
    thirdDigitHighlight.setCursor(Cursor.HAND);
  }

  public void darkeningThirdDigit() {
    thirdDigitHighlight.setOpacity(0);
  }

  /** This method activate the glow effect of roots. */
  public void activateRootGlow() {
    rootCollisionBox1.setOpacity(1);
    rootCollisionBox1.setCursor(Cursor.HAND);
    rootCollisionBox2.setOpacity(1);
    rootCollisionBox2.setCursor(Cursor.HAND);
    rootCollisionBox3.setOpacity(1);
    rootCollisionBox3.setCursor(Cursor.HAND);
  }

  /** Deactivates the glow effect of the root collision boxes. */
  public void deactivateRootGlow() {
    rootCollisionBox1.setOpacity(0);
    rootCollisionBox2.setOpacity(0);
    rootCollisionBox3.setOpacity(0);
  }

  /**
   * Pauses the tree flash animation, deactivates the tree glow, sets the UI to the chat panel, and
   * sets the previous scene to the chest panel.
   */
  public void goChat() {
    TreeAvatar.treeFlash.pause();
    TreeAvatar.deactivateTreeGlow();
    App.setUi(AppPanel.CHAT);
    SceneManager.setPrevious(AppPanel.CHEST);
  }

  public void miniTreeGlow() {
    miniTree.setEffect(GameState.glowBright);
  }

  public void miniTreeDim() {
    miniTree.setEffect(GameState.glowDim);
  }

  /** This method closes the info panel. */
  public void exitInfo() {
    collectedTitle.setVisible(false);
    collectedLabel.setVisible(false);
    controlPanelInfo.setVisible(false);
  }
}
