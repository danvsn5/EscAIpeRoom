package nz.ac.auckland.se206.controllers;

import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class ChestController {
  @FXML private Button backButton;
  @FXML private Button firstDigitButton;
  @FXML private Button secondDigitButton;
  @FXML private Button thirdDigitButton;
  @FXML private Button enterButton;
  @FXML private Label firstDigit;
  @FXML private Label secondDigit;
  @FXML private Label thirdDigit;

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
  }
}
