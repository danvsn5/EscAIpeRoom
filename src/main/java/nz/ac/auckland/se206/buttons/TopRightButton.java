package nz.ac.auckland.se206.buttons;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.util.Duration;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;

/** This class manages the top right buttons, change its color through out the time. */
public class TopRightButton {

  public static Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> cycle()));
  private static int cycleNumber = 2;
  private static ColorAdjust purpleColor = new ColorAdjust(-0.46, 0, 0, 0);
  private static ColorAdjust greenColor = new ColorAdjust(0.56, 0, 0, 0);
  private static ColorAdjust redColor = new ColorAdjust(0.0, 0, 0, 0);

  private static ColorAdjust blueColor = new ColorAdjust(-0.9, 0, 0, 0);

  /** This methods cycles through colors. */
  public static void cycle() {
    // 1: purple    2: blue     3: red    4: green
    switch (cycleNumber) {
      case 1:
        // if current color is purple, move to red
        changeColor("#topRightUnclicked", redColor);
        changeColor("#topRightClicked", redColor);

        cycleNumber = 2;
        break;
      case 2:
        // if current color is red, move to blue
        changeColor("#topRightUnclicked", blueColor);
        changeColor("#topRightClicked", blueColor);

        cycleNumber = 3;

        break;
      case 3:
        // if current color is blue, move to green
        changeColor("#topRightUnclicked", greenColor);
        changeColor("#topRightClicked", greenColor);

        cycleNumber = 4;
        break;
      case 4:
        // if current color is green, move to purple
        changeColor("#topRightUnclicked", purpleColor);
        changeColor("#topRightClicked", purpleColor);

        cycleNumber = 1;
        break;
    }
  }

  /**
   * This method changes the color of the button.
   *
   * @param id the id of the button.
   * @param color the color of the button.
   */
  public static void changeColor(String id, Effect color) {
    SceneManager.getPanel(AppPanel.THRUSTER).lookup(id).setEffect(color);
  }

  public static int getCycleNumber() {
    return cycleNumber;
  }

  public static Timeline getTimeline() {
    return timeline;
  }
}
