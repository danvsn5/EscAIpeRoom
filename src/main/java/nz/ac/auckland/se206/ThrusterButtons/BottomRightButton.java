package nz.ac.auckland.se206.ThrusterButtons;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.util.Duration;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class BottomRightButton {

  public static Timeline timeline = new Timeline(new KeyFrame(Duration.millis(750), e -> cycle()));
  private static int cycleNumber = 3;
  private static ColorAdjust purpleColor = new ColorAdjust(0, 0, 0, 0);
  private static ColorAdjust greenColor = new ColorAdjust(1, 0, 0, 0);
  private static ColorAdjust blueColor = new ColorAdjust(0.5, 0, 0, 0);

  private static ColorAdjust redColor = new ColorAdjust(-0.39, 0, 0, 0);

  public static void cycle() {
    // 1: purple    2: red    3: blue    4: orange
    switch (cycleNumber) {
      case 1:
        // if current color is purple, move to red
        visibleHelper("#bottomRightUnclicked", redColor);
        visibleHelper("#bottomRightClicked", redColor);

        cycleNumber = 2;
        break;
      case 2:
        // if current color is red, move to blue
        visibleHelper("#bottomRightUnclicked", blueColor);
        visibleHelper("#bottomRightClicked", blueColor);

        cycleNumber = 3;

        break;
      case 3:
        // if current color is blue, move to orange
        visibleHelper("#bottomRightUnclicked", greenColor);
        visibleHelper("#bottomRightClicked", greenColor);

        cycleNumber = 4;
        break;
      case 4:
        // if current color is orange, move to purple
        visibleHelper("#bottomRightUnclicked", purpleColor);
        visibleHelper("#bottomRightClicked", purpleColor);

        cycleNumber = 1;
        break;
    }
  }

  public static void visibleHelper(String id, Effect color) {
    SceneManager.getPanel(AppPanel.THRUSTER).lookup(id).setEffect(color);
  }

  public static int getCycleNumber() {
    return cycleNumber;
  }

  public static Timeline getTimeline() {
    return timeline;
  }
}
