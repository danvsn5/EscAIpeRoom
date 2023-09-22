package nz.ac.auckland.se206.thrusterButtons;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.util.Duration;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class BottomLeftButton {

  public static Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> cycle()));
  private static int cycleNumber = 1;
  private static ColorAdjust purpleColor = new ColorAdjust(0, 0, 0, 0);
  private static ColorAdjust greenColor = new ColorAdjust(1, 0, 0, 0);
  private static ColorAdjust redColor = new ColorAdjust(0.5, 0, 0, 0);

  private static ColorAdjust blueColor = new ColorAdjust(-0.39, 0, 0, 0);

  public static void cycle() {
    // 1: purple    2: blue     3: red    4: green
    switch (cycleNumber) {
      case 1:
        // if current color is purple, move to red
        visibleHelper("#bottomLeftUnclicked", redColor);
        visibleHelper("#bottomLeftClicked", redColor);

        cycleNumber = 2;
        break;
      case 2:
        // if current color is red, move to blue
        visibleHelper("#bottomLeftUnclicked", blueColor);
        visibleHelper("#bottomLeftClicked", blueColor);

        cycleNumber = 3;

        break;
      case 3:
        // if current color is blue, move to green
        visibleHelper("#bottomLeftUnclicked", greenColor);
        visibleHelper("#bottomLeftClicked", greenColor);

        cycleNumber = 4;
        break;
      case 4:
        // if current color is green, move to purple
        visibleHelper("#bottomLeftUnclicked", purpleColor);
        visibleHelper("#bottomLeftClicked", purpleColor);

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
