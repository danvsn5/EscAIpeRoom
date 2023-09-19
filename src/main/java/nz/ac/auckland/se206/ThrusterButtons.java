package nz.ac.auckland.se206;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class ThrusterButtons {

  public enum ThrusterButton {
    BOTTOM_LEFT_PURPLE_UNCLICKED,
    BOTTOM_LEFT_ORANGE_UNCLICKED,
    BOTTOM_LEFT_BLUE_UNCLICKED,
    BOTTOM_LEFT_RED_UNCLICKED,
    BOTTOM_LEFT_PURPLE_CLICKED,
    BOTTOM_LEFT_ORANGE_CLICKED,
    BOTTOM_LEFT_BLUE_CLICKED,
    BOTTOM_LEFT_RED_CLICKED
  }

  public static Timeline timeline =
      new Timeline(new KeyFrame(Duration.millis(1000), e -> cycleBottomLeft()));
  private static int cycleNumber = 1;

  public static void cycleBottomLeft() {

    switch (cycleNumber) {
      case 1:
        // if current color is purple, move to red
        visibleHelper("#bottomLeftUnclickedPurple", false);
        visibleHelper("#bottomLeftUnclickedRed", true);
        cycleNumber = 2;
        break;
      case 2:
        // if current color is red, move to blue
        visibleHelper("#bottomLeftUnclickedRed", false);
        visibleHelper("#bottomLeftUnclickedBlue", true);
        cycleNumber = 3;

        break;
      case 3:
        // if current color is blue, move to orange
        visibleHelper("#bottomLeftUnclickedBlue", false);
        visibleHelper("#bottomLeftUnclickedOrange", true);
        cycleNumber = 4;
        break;
      case 4:
        // if current color is orange, move to purple
        visibleHelper("#bottomLeftUnclickedOrange", false);
        visibleHelper("#bottomLeftUnclickedPurple", true);
        cycleNumber = 1;
        break;
    }
  }

  public static void visibleHelper(String id, boolean visibility) {
    SceneManager.getPanel(AppPanel.THRUSTER).lookup(id).setVisible(visibility);
  }
}
