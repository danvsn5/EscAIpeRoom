package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class ChestController {
  @FXML private Button backButton;
  @FXML private Button firstDigitUpButton;
  @FXML private Button firstDigitDownButton;
  @FXML private Button secondDigitUpButton;
  @FXML private Button secondDigitDownButton;
  @FXML private Button thirdDigitUpButton;
  @FXML private Button thirdDigitDownButton;
  @FXML private Label firstDigit;
  @FXML private Label secondDigit;
  @FXML private Label thirdDigit;

  private int firstDigitNum = 0;
  private int secondDigitNum = 0;
  private int thirdDigitNum = 0;

  public void goBack() {
    App.setUi(AppPanel.MAIN_ROOM);
  }

  public void firstDigitUp() {
    firstDigitNum++;
  }
}
