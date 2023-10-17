package nz.ac.auckland.se206;

import nz.ac.auckland.se206.SceneManager.AppPanel;

/** This class controls all the root image in game. */
public class RootBorder {
  // Record the current rootState
  public static int rootState = 1;

  /** Change the image of the root according to the current state. */
  public static void rootGrow() {
    rootState++;
    // Switch through different states
    switch (rootState) {
      case 2:
        // If the current state is 2, hide image one and show image two
        rootRemoveHelper("#root1");
        rootGrowHelper("#root2");

        break;
      case 3:
        // If the current state is 3, hide image two and show image three
        rootRemoveHelper("#root2");
        rootGrowHelper("#root3");
        break;
      case 4:
        // If the current state is 4, hide the image three and show image four
        rootRemoveHelper("#root3");
        rootGrowHelper("#root4");
        break;
    }
  }

  /**
   * Show the input root image in every panel.
   *
   * @param rootId fx id of the root that needs to be changed.
   */
  public static void rootGrowHelper(String rootId) {
    // Find the root image in every panel and change it
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup(rootId).setVisible(true);
    SceneManager.getPanel(AppPanel.OUTSIDE).lookup(rootId).setVisible(true);
    SceneManager.getPanel(AppPanel.STORAGE).lookup(rootId).setVisible(true);
    if (GameState.isGreetingShown) {
      activateAllCollisionBox();
    }
    deactivateAllCollisionBox();
  }

  /**
   * Hide the input root image in every panel.
   *
   * @param rootId fx id of the root that needs to be changed.
   */
  public static void rootRemoveHelper(String rootId) {
    // Find the root image in every panel and change it
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup(rootId).setVisible(false);
    SceneManager.getPanel(AppPanel.OUTSIDE).lookup(rootId).setVisible(false);
    SceneManager.getPanel(AppPanel.STORAGE).lookup(rootId).setVisible(false);
  }

  public static void activateAllCollisionBox() {
    activateShipRootCollisionBox(AppPanel.MAIN_ROOM);
    activateShipRootCollisionBox(AppPanel.STORAGE);
  }

  public static void deactivateAllCollisionBox() {
    deactivateShipRootCollisionBox(AppPanel.MAIN_ROOM);
    deactivateShipRootCollisionBox(AppPanel.STORAGE);
  }

  /**
   * Show the collision box of root in every panel according to current state.
   *
   * @param panel the panel that the root needs to be changed.
   */
  public static void activateShipRootCollisionBox(AppPanel panel) {
    // Activate the collision box
    switch (rootState) {
      case 1:
        // If it is state 1, activate state 1 collision box
        SceneManager.getPanel(panel).lookup("#rootOneCollisionBox1").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootOneCollisionBox2").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootOneCollisionBox3").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootOneCollisionBox4").setVisible(true);
        break;
      case 2:
        // If it is state 2, activate state 2 collision box
        SceneManager.getPanel(panel).lookup("#rootTwoCollisionBox1").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootTwoCollisionBox2").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootTwoCollisionBox3").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootTwoCollisionBox4").setVisible(true);
        break;
      case 3:
        // If it is state 3, activate state 3 collision box
        SceneManager.getPanel(panel).lookup("#rootThreeCollisionBox1").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootThreeCollisionBox2").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootThreeCollisionBox3").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootThreeCollisionBox4").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootThreeCollisionBox5").setVisible(true);
        break;
      case 4:
        // If it is state 4, activate state 4 collision box
        SceneManager.getPanel(panel).lookup("#rootFourCollisionBox1").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootFourCollisionBox2").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootFourCollisionBox3").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootFourCollisionBox4").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootFourCollisionBox5").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootFourCollisionBox6").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootFourCollisionBox7").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootFourCollisionBox8").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootFourCollisionBox9").setVisible(true);
        break;
      default:
        break;
    }
    // Activate the collision box for thruster root and chest root no matter what state is
    SceneManager.getPanel(AppPanel.THRUSTER).lookup("#rootCollisionBox1").setVisible(true);
    SceneManager.getPanel(AppPanel.THRUSTER).lookup("#rootCollisionBox2").setVisible(true);
    SceneManager.getPanel(AppPanel.THRUSTER).lookup("#rootCollisionBox3").setVisible(true);
    SceneManager.getPanel(AppPanel.CHEST).lookup("#rootCollisionBox1").setVisible(true);
    SceneManager.getPanel(AppPanel.CHEST).lookup("#rootCollisionBox2").setVisible(true);
    SceneManager.getPanel(AppPanel.CHEST).lookup("#rootCollisionBox3").setVisible(true);
  }

  /**
   * Hide the collision box of root in every panel according to current state.
   *
   * @param panel the panel that the root needs to be changed.
   */
  public static void deactivateShipRootCollisionBox(AppPanel panel) {
    // Deactivate the collision box
    switch (rootState) {
      case 2:
        // If it is currently state 2, deactivate all the collision box in state 1
        SceneManager.getPanel(panel).lookup("#rootOneCollisionBox1").setVisible(false);
        SceneManager.getPanel(panel).lookup("#rootOneCollisionBox2").setVisible(false);
        SceneManager.getPanel(panel).lookup("#rootOneCollisionBox3").setVisible(false);
        SceneManager.getPanel(panel).lookup("#rootOneCollisionBox4").setVisible(false);
        break;
      case 3:
        // If it is currently state 3, deactivate all the collision box in state 2
        SceneManager.getPanel(panel).lookup("#rootTwoCollisionBox1").setVisible(false);
        SceneManager.getPanel(panel).lookup("#rootTwoCollisionBox2").setVisible(false);
        SceneManager.getPanel(panel).lookup("#rootTwoCollisionBox3").setVisible(false);
        SceneManager.getPanel(panel).lookup("#rootTwoCollisionBox4").setVisible(false);
        break;
      case 4:
        // If it is currently state 4, deactivate all the collision box in state 3
        SceneManager.getPanel(panel).lookup("#rootThreeCollisionBox1").setVisible(false);
        SceneManager.getPanel(panel).lookup("#rootThreeCollisionBox2").setVisible(false);
        SceneManager.getPanel(panel).lookup("#rootThreeCollisionBox3").setVisible(false);
        SceneManager.getPanel(panel).lookup("#rootThreeCollisionBox4").setVisible(false);
        SceneManager.getPanel(panel).lookup("#rootThreeCollisionBox5").setVisible(false);
        break;
      default:
        break;
    }
  }
}
