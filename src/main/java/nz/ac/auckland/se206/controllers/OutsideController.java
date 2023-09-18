package nz.ac.auckland.se206.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;
import nz.ac.SceneManager;
import nz.ac.SceneManager.AppPanel;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

public class OutsideController {
  @FXML private Ellipse outsideReturnDoor;
  @FXML private Circle sand;
  @FXML private Circle tech;
  @FXML private Circle wiseTree;
  @FXML private Label counter;
  @FXML private Label sandLabel;
  @FXML private Label treeLabel;
  @FXML private Label shipLabel;
  @FXML private Label techLabel;

  public void initialize() {
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> dispCount()));

    timeline.setCycleCount(123);
    timeline.play();
  }

  // displays counter on panel and constantly checks if the riddle has been solved. If riddle was
  // solved correctly, and sand is currently NOT in the inventory, then the sand appears inside the
  // panel.
  public void dispCount() {
    counter.setText(String.valueOf(GameState.count));

    if (GameState.inventory.contains(-1) && !GameState.inventory.contains(2)) {
      sand.setVisible(true);
    }
  }

  public void outsideReturn() {
    SceneManager.setPrevious(AppPanel.MAIN_ROOM);
    App.setUi(AppPanel.MAIN_ROOM);
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

    if (GameState.inventory.contains(-1)) {
      GameState.inventory.add(2);
      sand.setVisible(false);
    }
  }

  // there are two types of methods below: Light and Dark/Normal. On hover over with mouse, Light
  // method is invoked: the color of the selected object becomes lighter and a label becomes
  // visible, indicating it is
  // clickble. Once mouse is moved from object, color returns to original and the label is made
  // invisible with Dark/Normal method
  // invokation.
  public void outsideReturnDoorLight() {
    outsideReturnDoor.setFill(Color.valueOf("63b4ff"));
    shipLabel.setVisible(true);
  }

  public void outsideReturnDoorNormal() {
    outsideReturnDoor.setFill(Color.valueOf("1e90ff"));
    shipLabel.setVisible(false);
  }

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
    sandLabel.setVisible(true);
  }

  public void sandNormal() {
    sand.setFill(Color.valueOf("fffba5"));
    sandLabel.setVisible(false);
  }

  public void openRiddle() {
    SceneManager.setPrevious(AppPanel.CHAT);
    App.setUi(AppPanel.CHAT);
  }
}
