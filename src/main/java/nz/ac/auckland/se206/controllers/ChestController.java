package nz.ac.auckland.se206.controllers;

import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.MissionManager.MISSION;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.TreeAvatar;

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
  @FXML private ImageView rootInitial;
  @FXML private ImageView rootOne;
  @FXML private ImageView rootTwo;
  @FXML private ImageView rootThree;

  private int firstDigitNum = 0;
  private int secondDigitNum = 0;
  private int thirdDigitNum = 0;
  private Random rand;
  private int correctPassword;

  public ChestController() {
    rand = new Random();
    correctPassword = rand.nextInt(1000);
    System.out.println(getCorrectPassword());
  }

  public void goBack() {
    App.setUi(AppPanel.STORAGE);
  }

  public void goProgress() {
    SceneManager.setPrevious(AppPanel.CHEST);
    App.setUi(AppPanel.PROGRESS);
  }

  public void firstDigitUp() {
    firstDigitNum++;
    if (firstDigitNum >= 10) {
      firstDigitNum = 0;
    }
    firstDigit.setText(Integer.toString(firstDigitNum));
  }

  public void secondDigitUp() {
    secondDigitNum++;
    if (secondDigitNum >= 10) {
      secondDigitNum = 0;
    }
    secondDigit.setText(Integer.toString(secondDigitNum));
  }

  public void thirdDigitUp() {
    thirdDigitNum++;
    if (thirdDigitNum >= 10) {
      thirdDigitNum = 0;
    }
    thirdDigit.setText(Integer.toString(thirdDigitNum));
  }

  public void check() {
    int password = firstDigitNum * 100 + secondDigitNum * 10 + thirdDigitNum;
    if (password == GameState.passWord) {
      GameState.missionManager.getMission(MISSION.CONTROLLER).increaseStage();
      GameState.progressBarGroup.updateProgressOne(MISSION.CONTROLLER);
      SceneManager.showDialog(
          "Info", "Control panel collected", "The spare part of the controller");
      disableLock();
    } else {
      SceneManager.showDialog("Info", "Wrong password", "Access denied");
    }
  }

  public String getCorrectPassword() {
    String result;
    if (correctPassword < 10) {
      result = "00" + Integer.toString(correctPassword);
    } else if (correctPassword >= 10 && correctPassword < 100) {
      result = "0" + Integer.toString(correctPassword);
    } else {
      result = Integer.toString(correctPassword);
    }
    return result;
  }

  public void disableLock() {
    enterButton.setOpacity(0);
    enterButton.setDisable(true);
    firstDigitHighlight.setDisable(true);
    secondDigitHighlight.setDisable(true);
    thirdDigitHighlight.setDisable(true);
  }

  public void activateProgressGlow() {
    progressButton.setEffect(GameState.glowBright);
  }

  public void deactivateProgressGlow() {
    progressButton.setEffect(GameState.glowDim);
  }

  public void firstDigitGlow() {
    firstDigitHighlight.setOpacity(1);
  }

  public void firstDigitDark() {
    firstDigitHighlight.setOpacity(0);
  }

  public void secondDigitGlow() {
    secondDigitHighlight.setOpacity(1);
  }

  public void secondDigitDark() {
    secondDigitHighlight.setOpacity(0);
  }

  public void thirdDigitGlow() {
    thirdDigitHighlight.setOpacity(1);
  }

  public void thirdDigitDark() {
    thirdDigitHighlight.setOpacity(0);
  }

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
}
