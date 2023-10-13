package nz.ac.auckland.se206;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.Glow;
import javafx.util.Duration;
import nz.ac.auckland.se206.SceneManager.AppPanel;

/** This class controls the mini tree icons in every room */
public class TreeAvatar {

  public static Timeline treeFlash =
      new Timeline(new KeyFrame(Duration.millis(750), e -> flashTree()));
  private static int treeState = 0;
  private static Glow glowDim = new Glow(0.0);
  private static Glow glowBright = new Glow(0.7);

  /** Activate the flashing feature of minitree */
  public static void startFlashTree() {
    // The tree changes light every 0.75s
    treeFlash.setCycleCount(Timeline.INDEFINITE);
    treeFlash.play();
  }

  /** Activate the minitree icon glow in each panel */
  public static void flashTree() {
    // If the tree state is 0, indicating the tree is dim, set them to bright
    if (treeState == 0) {

      switch (RootBorder.rootState) {
        case 1:
          rootOneActivate();
          break;
        case 2:
          rootTwoActivate();
          break;
        case 3:
          rootThreeActivate();
          break;
        case 4:
          rootFourActivate();
          break;
      }

      // Set the tree state to 1
      treeState = 1;
    } else {
      // If the tree state is 1, indicating the tree is bright, set them to dim
      switch (RootBorder.rootState) {
        case 1:
          rootOneDeactivate();
          break;
        case 2:
          rootTwoDeactivate();
          break;
        case 3:
          rootThreeDeactivate();
          break;
        case 4:
          rootFourDeactivate();
          break;
      }
      treeState = 0;
    }
  }

  /** Deactivate the minitree icon glow in each panel */
  public static void deactivateTreeGlow() {
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#miniTree").setEffect(glowDim);
    SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#miniTree").setEffect(glowDim);
    SceneManager.getPanel(AppPanel.THRUSTER).lookup("#miniTree").setEffect(glowDim);
    SceneManager.getPanel(AppPanel.CHEST).lookup("#miniTree").setEffect(glowDim);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#miniTree").setEffect(glowDim);
  }

  /** Set the minitree icon in each panel to visible */
  public static void setTreeVisible() {
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#miniTree").setVisible(true);
    SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#miniTree").setVisible(true);
    SceneManager.getPanel(AppPanel.THRUSTER).lookup("#miniTree").setVisible(true);
    SceneManager.getPanel(AppPanel.CHEST).lookup("#miniTree").setVisible(true);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#miniTree").setVisible(true);
  }

  public static void rootOneActivate() {
    /* ========================================== Central Room ========================================== */
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootOneCollisionBox1").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootOneCollisionBox2").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootOneCollisionBox3").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootOneCollisionBox4").setOpacity(1);

    /* ========================================== Storage Room ========================================== */
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootOneCollisionBox1").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootOneCollisionBox2").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootOneCollisionBox3").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootOneCollisionBox4").setOpacity(1);
  }

  public static void rootOneDeactivate() {
    /* ========================================== Central Room ========================================== */
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootOneCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootOneCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootOneCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootOneCollisionBox4").setOpacity(0);

    /* ========================================== Storage Room ========================================== */
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootOneCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootOneCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootOneCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootOneCollisionBox4").setOpacity(0);
  }

  public static void rootTwoActivate() {
    /* ========================================== Central Room ========================================== */
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootTwoCollisionBox1").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootTwoCollisionBox2").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootTwoCollisionBox3").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootTwoCollisionBox4").setOpacity(1);

    /* ========================================== Storage Room ========================================== */
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootTwoCollisionBox1").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootTwoCollisionBox2").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootTwoCollisionBox3").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootTwoCollisionBox4").setOpacity(1);
  }

  public static void rootTwoDeactivate() {
    /* ========================================== Central Room ========================================== */
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootTwoCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootTwoCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootTwoCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootTwoCollisionBox4").setOpacity(0);

    /* ========================================== Storage Room ========================================== */
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootTwoCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootTwoCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootTwoCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootTwoCollisionBox4").setOpacity(0);
  }

  public static void rootThreeActivate() {
    /* ========================================== Central Room ========================================== */
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootThreeCollisionBox1").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootThreeCollisionBox2").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootThreeCollisionBox3").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootThreeCollisionBox4").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootThreeCollisionBox5").setOpacity(1);

    /* ========================================== Storage Room ========================================== */
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootThreeCollisionBox1").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootThreeCollisionBox2").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootThreeCollisionBox3").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootThreeCollisionBox4").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootThreeCollisionBox5").setOpacity(1);
  }

  public static void rootThreeDeactivate() {
    /* ========================================== Central Room ========================================== */
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootThreeCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootThreeCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootThreeCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootThreeCollisionBox4").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootThreeCollisionBox5").setOpacity(0);

    /* ========================================== Storage Room ========================================== */
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootThreeCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootThreeCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootThreeCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootThreeCollisionBox4").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootThreeCollisionBox5").setOpacity(0);
  }

  public static void rootFourActivate() {
    /* ========================================== Central Room ========================================== */
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox1").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox2").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox3").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox4").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox5").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox6").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox7").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox8").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox9").setOpacity(1);

    /* ========================================== Storage Room ========================================== */
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox1").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox2").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox3").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox4").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox5").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox6").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox7").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox8").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox9").setOpacity(1);
  }

  public static void rootFourDeactivate() {
    /* ========================================== Central Room ========================================== */
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox4").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox5").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox6").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox7").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox8").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootFourCollisionBox9").setOpacity(0);

    /* ========================================== Storage Room ========================================== */
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox4").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox5").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox6").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox7").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox8").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#rootFourCollisionBox9").setOpacity(0);
  }
}
