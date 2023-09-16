package nz.ac.auckland;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import nz.ac.SceneManager;
import nz.ac.SceneManager.AppPanel;

public class RootBorder {

  public static int rootState = 0;

  public static Timeline treeTimelineTwo =
      new Timeline(new KeyFrame(Duration.seconds(30), e -> rootGrow()));

  public static Timeline treeTimelineFour =
      new Timeline(new KeyFrame(Duration.seconds(60), e -> rootGrow()));

  public static Timeline treeTimelineSix =
      new Timeline(new KeyFrame(Duration.seconds(90), e -> rootGrow()));

  public static int getRootState() {
    return RootBorder.rootState;
  }

  public static void incrementRootState() {
    RootBorder.rootState++;
  }

  public static void rootGrow() {
    // gets the current root state of the game. Depending on the current state, the roots will grow
    // in size across the border of the screen in all panels except the launch panel
    int currentRootState = RootBorder.getRootState();

    switch (currentRootState) {
      case 0:
        rootRemoveHelper("#rootInitial");
        rootGrowHelper("#rootOne");
        incrementRootState();
        break;
      case 1:
        rootRemoveHelper("#rootOne");
        rootGrowHelper("#rootTwo");
        incrementRootState();
        break;
      case 2:
        rootRemoveHelper("#rootTwo");
        rootGrowHelper("#rootThree");
        break;
    }
  }

  public static void rootGrowHelper(String rootId) {
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup(rootId).setVisible(true);
    // SceneManager.getPanel(AppPanel.WORK).lookup(rootId).setVisible(true);
    // SceneManager.getPanel(AppPanel.OUTSIDE).lookup(rootId).setVisible(true);
    // SceneManager.getPanel(AppPanel.CHAT).lookup(rootId).setVisible(true);
  }

  public static void rootRemoveHelper(String rootId) {
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup(rootId).setVisible(false);
    // SceneManager.getPanel(AppPanel.WORK).lookup(rootId).setVisible(false);
    // SceneManager.getPanel(AppPanel.OUTSIDE).lookup(rootId).setVisible(false);
    // SceneManager.getPanel(AppPanel.CHAT).lookup(rootId).setVisible(false);
  }
}
