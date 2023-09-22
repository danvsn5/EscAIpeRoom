package nz.ac.auckland.se206;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import nz.ac.auckland.se206.SceneManager.AppPanel;

/** This class controls all the root image in game */
public class RootBorder {
  // Record the current rootState
  public static int rootState = 0;

  // Record the time passed to invoke method
  public static Timeline treeTimelineTwo =
      new Timeline(new KeyFrame(Duration.seconds(30), e -> rootGrow()));

  public static Timeline treeTimelineFour =
      new Timeline(new KeyFrame(Duration.seconds(60), e -> rootGrow()));

  public static Timeline treeTimelineSix =
      new Timeline(new KeyFrame(Duration.seconds(90), e -> rootGrow()));

  /**
   * Get the current stage of the root
   *
   * @return The stage of root
   */
  public static int getRootState() {
    return RootBorder.rootState;
  }

  /** Increase the Root state by 1 */
  public static void incrementRootState() {
    RootBorder.rootState++;
  }

  /** Change the image of the root according to the current state */
  public static void rootGrow() {
    // gets the current root state of the game. Depending on the current state, the roots will grow
    // in size across the border of the screen in all panels except the launch panel
    int currentRootState = RootBorder.getRootState();

    // Switch through different states
    switch (currentRootState) {
      case 0:
        // If the current state is 0, hide the initial image and show image one
        rootRemoveHelper("#rootInitial");
        rootGrowHelper("#rootOne");
        // Increase the current state
        incrementRootState();
        break;
      case 1:
        // If the current state is 1, hide image one and show image two
        rootRemoveHelper("#rootOne");
        rootGrowHelper("#rootTwo");
        // Increase the current state
        incrementRootState();
        break;
      case 2:
        // If the current state is 2, hide the image two and show image three
        rootRemoveHelper("#rootTwo");
        rootGrowHelper("#rootThree");
        break;
    }
  }

  /**
   * Show the input root image in every panel
   *
   * @param rootId fx id of the root that needs to be changed).
   */
  public static void rootGrowHelper(String rootId) {
    // Find the root image in every panel and change it
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup(rootId).setVisible(true);
    SceneManager.getPanel(AppPanel.OUTSIDE).lookup(rootId).setVisible(true);
    SceneManager.getPanel(AppPanel.STORAGE).lookup(rootId).setVisible(true);
    SceneManager.getPanel(AppPanel.THRUSTER).lookup(rootId).setVisible(true);
    SceneManager.getPanel(AppPanel.CHEST).lookup(rootId).setVisible(true);
    SceneManager.getPanel(AppPanel.PROGRESS).lookup(rootId).setVisible(true);
    SceneManager.getPanel(AppPanel.CHAT).lookup(rootId).setVisible(true);
  }

  /**
   * Hide the input root image in every panel
   *
   * @param rootId fx id of the root that needs to be changed).
   */
  public static void rootRemoveHelper(String rootId) {
    // Find the root image in every panel and change it
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup(rootId).setVisible(false);
    SceneManager.getPanel(AppPanel.OUTSIDE).lookup(rootId).setVisible(false);
    SceneManager.getPanel(AppPanel.STORAGE).lookup(rootId).setVisible(false);
    SceneManager.getPanel(AppPanel.THRUSTER).lookup(rootId).setVisible(false);
    SceneManager.getPanel(AppPanel.CHEST).lookup(rootId).setVisible(false);
    SceneManager.getPanel(AppPanel.PROGRESS).lookup(rootId).setVisible(false);
    SceneManager.getPanel(AppPanel.CHAT).lookup(rootId).setVisible(false);
  }
}
