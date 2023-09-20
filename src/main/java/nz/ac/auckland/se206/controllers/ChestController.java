package nz.ac.auckland.se206.controllers;

import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class ChestController {
  @FXML private Button backButton;
  @FXML private Button enterButton;
  @FXML private Label firstDigit;
  @FXML private Label secondDigit;
  @FXML private Label thirdDigit;
  @FXML private Rectangle firstDigitHighlight;
  @FXML private Rectangle secondDigitHighlight;
  @FXML private Rectangle thirdDigitHighlight;
  @FXML private ImageView miniTree;

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
    App.setUi(AppPanel.MAIN_ROOM);
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
    if (password == correctPassword) {
      openChest();
      disableLock();
    } else {
      System.out.println("Wrong password");
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

  public void openChest() {
    System.out.println("Chest opened");
    System.out.println("Controll panel collected");
  }

  public void disableLock() {
    enterButton.setOpacity(0);
    enterButton.setDisable(true);
    firstDigitHighlight.setDisable(true);
    secondDigitHighlight.setDisable(true);
    thirdDigitHighlight.setDisable(true);
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
    App.setUi(AppPanel.CHAT);
  }

  public void miniTreeGlow() {
    miniTree.setEffect(GameState.glowBright);
  }

  public void miniTreeDim() {
    miniTree.setEffect(GameState.glowDim);
  }
}
