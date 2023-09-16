package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import nz.ac.SceneManager.AppPanel;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

public class CentralController {
  @FXML private Rectangle outsideDoor;
  @FXML private Rectangle workDoor;
  @FXML private Circle hammer;
  @FXML private Line crackOne;
  @FXML private Line crackTwo;
  @FXML private Line purpleStick;
  @FXML private Line greenStick;
  @FXML private Line blueStick;
  @FXML private Circle knobG;
  @FXML private Circle knobB;
  @FXML private Circle knobP;
  @FXML private Circle redButton;
  @FXML private Label counter;
  @FXML private Label introLabel;
  @FXML private Rectangle cross;
  @FXML private Rectangle introBackground;
  @FXML private Line crossOne;
  @FXML private Line crossTwo;
  @FXML private Ellipse window;
  @FXML private Rectangle ctrlPanel;
  @FXML private Label outLabel;
  @FXML private Label windowLabel;
  @FXML private Label hammerLabel;
  @FXML private Label workLabel;

  @FXML private Label panelLabel;

  public void initialize() {
    // Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> dispCount()));

    // timeline.setCycleCount(123);
    // timeline.play();
  }

  public void dispCount(String time) {
    // counter.setText(String.valueOf(GameState.count));
    counter.setText(time);
  }

  public void goOutside() {
    App.setUi(AppPanel.OUTSIDE);
  }

  public void goWorkshop() {
    App.setUi(AppPanel.WORK);
  }

  public void collectHammer() {
    GameState.inventory.add(0);
    hammer.setVisible(false);
  }

  // if inventory contains the necessary items, fixes the window and control panel and changes
  // visibility of assets
  public void repairWindow() {
    if (GameState.inventory.contains(4)) {
      GameState.inventory.add(6);
      crackOne.setVisible(false);
      crackTwo.setVisible(false);
    }
  }

  public void repairPanel() {
    if (GameState.inventory.contains(5)) {
      GameState.inventory.add(7);
      purpleStick.setVisible(true);
      blueStick.setVisible(true);
      greenStick.setVisible(true);
      knobB.setVisible(true);
      knobG.setVisible(true);
      knobP.setVisible(true);
      redButton.setVisible(true);
    }
  }

  // closes the intro message by pressing the cross on the top left of the main room panel
  public void closeMessage() {
    introBackground.setVisible(false);
    introLabel.setVisible(false);
    cross.setVisible(false);
    crossOne.setVisible(false);
    crossTwo.setVisible(false);
  }

  // if window and control panel are fixed, then game can be completed by pressing red button
  public void goHome() {
    if (GameState.inventory.contains(6) && GameState.inventory.contains(7)) {
      GameState.timeline.playFromStart();
      App.setUi(AppPanel.WIN);
    }
  }

  // there are two types of methods below: Light and Dark/Normal. On hover over with mouse, Light
  // method is invoked: the color of the selected object becomes lighter and a label becomes
  // visible, indicating it is
  // clickble. Once mouse is moved from object, color returns to original and the label is made
  // invisible with Dark/Normal method
  // invokation.
  public void outsideDoorLight() {
    outsideDoor.setFill(Color.valueOf("835339"));
    outLabel.setVisible(true);
  }

  public void outsideDoorDark() {
    outsideDoor.setFill(Color.valueOf("653920"));
    outLabel.setVisible(false);
  }

  public void workDoorLight() {
    workDoor.setFill(Color.valueOf("835339"));
    workLabel.setVisible(true);
  }

  public void workDoorNormal() {
    workDoor.setFill(Color.valueOf("653920"));
    workLabel.setVisible(false);
  }

  public void windowLight() {
    if (!GameState.inventory.contains(6)) {
      window.setFill(Color.valueOf("dbecfc"));
      window.setCursor(Cursor.CLOSED_HAND);
      windowLabel.setVisible(true);

    } else {
      window.setCursor(Cursor.DEFAULT);
      windowLabel.setVisible(false);
    }
  }

  public void windowDark() {
    if (!GameState.inventory.contains(6)) {
      window.setFill(Color.valueOf("bee0ff"));
      window.setCursor(Cursor.CLOSED_HAND);
      windowLabel.setVisible(false);
    } else {
      windowLabel.setVisible(false);
    }
  }

  public void hammerLight() {
    hammer.setFill(Color.valueOf("7f858a"));
    hammerLabel.setVisible(true);
  }

  public void hammerNormal() {
    hammer.setFill(Color.valueOf("5a636b"));
    hammerLabel.setVisible(false);
  }

  public void ctrlLight() {
    if (!GameState.inventory.contains(7)) {
      ctrlPanel.setFill(Color.valueOf("7f858a"));
      panelLabel.setVisible(true);
    } else {
      ctrlPanel.setCursor(Cursor.DEFAULT);
      panelLabel.setVisible(false);
    }
  }

  public void ctrlNormal() {
    if (!GameState.inventory.contains(7)) {
      ctrlPanel.setFill(Color.valueOf("5a636b"));
      panelLabel.setVisible(false);
    } else {
      panelLabel.setVisible(false);
    }
  }

  public void redButtonLight() {
    redButton.setFill(Color.valueOf("ff4c4c"));
  }

  public void redButtonNormal() {
    redButton.setFill(Color.valueOf("ff1f1f"));
  }
}
