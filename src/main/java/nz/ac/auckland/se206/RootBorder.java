package nz.ac.auckland.se206;

import nz.ac.auckland.se206.SceneManager.AppPanel;

/** This class controls all the root image in game */
public class RootBorder {
  // Record the current rootState
  public static int rootState = 1;

  /** Change the image of the root according to the current state */
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
   * Show the input root image in every panel
   *
   * @param rootId fx id of the root that needs to be changed).
   */
  public static void rootGrowHelper(String rootId) {
    // Find the root image in every panel and change it
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup(rootId).setVisible(true);
    SceneManager.getPanel(AppPanel.OUTSIDE).lookup(rootId).setVisible(true);
    SceneManager.getPanel(AppPanel.STORAGE).lookup(rootId).setVisible(true);
    activateAllCollisionBox();
    deactivateAllCollisionBox();
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
    deactivateShipRootCollisionBox(AppPanel.MAIN_ROOM);
    deactivateShipRootCollisionBox(AppPanel.STORAGE);
  }

  public static void activateAllCollisionBox() {
    activateShipRootCollisionBox(AppPanel.MAIN_ROOM);
    activateShipRootCollisionBox(AppPanel.STORAGE);
  }

  public static void deactivateAllCollisionBox() {
    deactivateShipRootCollisionBox(AppPanel.MAIN_ROOM);
    deactivateShipRootCollisionBox(AppPanel.STORAGE);
  }

  public static void activateShipRootCollisionBox(AppPanel panel) {
    // Activate the collision box
    switch (rootState) {
      case 1:
        SceneManager.getPanel(panel).lookup("#rootOneCollisionBox1").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootOneCollisionBox2").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootOneCollisionBox3").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootOneCollisionBox4").setVisible(true);
        break;
      case 2:
        SceneManager.getPanel(panel).lookup("#rootTwoCollisionBox1").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootTwoCollisionBox2").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootTwoCollisionBox3").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootTwoCollisionBox4").setVisible(true);
        break;
      case 3:
        SceneManager.getPanel(panel).lookup("#rootThreeCollisionBox1").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootThreeCollisionBox2").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootThreeCollisionBox3").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootThreeCollisionBox4").setVisible(true);
        SceneManager.getPanel(panel).lookup("#rootThreeCollisionBox5").setVisible(true);
        break;
      case 4:
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
    SceneManager.getPanel(AppPanel.CHEST).lookup("#rootCollisionBox1").setVisible(true);
    SceneManager.getPanel(AppPanel.CHEST).lookup("#rootCollisionBox2").setVisible(true);
    SceneManager.getPanel(AppPanel.CHEST).lookup("#rootCollisionBox3").setVisible(true);
  }

  public static void deactivateShipRootCollisionBox(AppPanel panel) {
    // Deactivate the collision box
    switch (rootState) {
      case 2:
        SceneManager.getPanel(panel).lookup("#rootOneCollisionBox1").setVisible(false);
        SceneManager.getPanel(panel).lookup("#rootOneCollisionBox2").setVisible(false);
        SceneManager.getPanel(panel).lookup("#rootOneCollisionBox3").setVisible(false);
        SceneManager.getPanel(panel).lookup("#rootOneCollisionBox4").setVisible(false);
        break;
      case 3:
        SceneManager.getPanel(panel).lookup("#rootTwoCollisionBox1").setVisible(false);
        SceneManager.getPanel(panel).lookup("#rootTwoCollisionBox2").setVisible(false);
        SceneManager.getPanel(panel).lookup("#rootTwoCollisionBox3").setVisible(false);
        SceneManager.getPanel(panel).lookup("#rootTwoCollisionBox4").setVisible(false);
        break;
      case 4:
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
