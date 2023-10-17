package nz.ac.auckland.se206;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import nz.ac.auckland.se206.SceneManager.AppPanel;

/** This class controls the mini tree icons in every room. */
public class TreeAvatar {

  public static Timeline treeFlash =
      new Timeline(new KeyFrame(Duration.millis(750), e -> flashTree()));
  public static int treeState = 0;

  /** Activate the flashing feature of minitree. */
  public static void startFlashTree() {
    // The tree changes light every 0.75s
    treeFlash.setCycleCount(Timeline.INDEFINITE);
    treeFlash.play();
  }

  /** Activate the minitree icon glow in each panel. */
  public static void flashTree() {
    // If the tree state is 0, indicating the tree is dim, set them to bright
    if (treeState == 0) {

      SceneManager.getPanel(AppPanel.CHEST).lookup("#rootCollisionBox1").setOpacity(1);
      SceneManager.getPanel(AppPanel.CHEST).lookup("#rootCollisionBox2").setOpacity(1);
      SceneManager.getPanel(AppPanel.CHEST).lookup("#rootCollisionBox3").setOpacity(1);
      SceneManager.getPanel(AppPanel.THRUSTER).lookup("#rootCollisionBox1").setOpacity(1);
      SceneManager.getPanel(AppPanel.THRUSTER).lookup("#rootCollisionBox2").setOpacity(1);
      SceneManager.getPanel(AppPanel.THRUSTER).lookup("#rootCollisionBox3").setOpacity(1);

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
      deactivateTreeGlow();
      treeState = 0;
    }
  }

  /** Deactivate the minitree icon glow in each panel. */
  public static void deactivateTreeGlow() {
    // Deactivate root collision box glow in chest and thruster panel
    SceneManager.getPanel(AppPanel.CHEST).lookup("#rootCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.CHEST).lookup("#rootCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.CHEST).lookup("#rootCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.THRUSTER).lookup("#rootCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.THRUSTER).lookup("#rootCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.THRUSTER).lookup("#rootCollisionBox3").setOpacity(0);
    // Deactivate root collision box glow in main room and storage room
    rootFourDeactivate();
    rootOneDeactivate();
    rootThreeDeactivate();
    rootTwoDeactivate();
  }

  /** Set the minitree icon in each panel to visible. */
  public static void setTreeVisible() {
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#miniTree").setVisible(true);
    SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#miniTree").setVisible(true);
    SceneManager.getPanel(AppPanel.THRUSTER).lookup("#miniTree").setVisible(true);
    SceneManager.getPanel(AppPanel.CHEST).lookup("#miniTree").setVisible(true);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#miniTree").setVisible(true);
  }

  /** Activate root one collision box in main room and storage room. */
  public static void rootOneActivate() {
    /* === Central Room === */
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootOneCollisionBox1").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootOneCollisionBox2").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootOneCollisionBox3").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootOneCollisionBox4").setOpacity(1);

    /* === Storage Room === */
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootOneCollisionBox1").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootOneCollisionBox2").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootOneCollisionBox3").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootOneCollisionBox4").setOpacity(1);
  }

  /** Deactivate root one collision box in main room and storage room. */
  public static void rootOneDeactivate() {
    /* === Central Room === */
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootOneCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootOneCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootOneCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootOneCollisionBox4").setOpacity(0);

    /* === Storage Room === */
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootOneCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootOneCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootOneCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootOneCollisionBox4").setOpacity(0);
  }

  /** Activate root two collision box in main room and storage room. */
  public static void rootTwoActivate() {
    /* === Central Room === */
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootTwoCollisionBox1").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootTwoCollisionBox2").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootTwoCollisionBox3").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootTwoCollisionBox4").setOpacity(1);

    /* === Storage Room === */
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootTwoCollisionBox1").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootTwoCollisionBox2").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootTwoCollisionBox3").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootTwoCollisionBox4").setOpacity(1);
  }

  /** Deactivate root two collision box in main room and storage room. */
  public static void rootTwoDeactivate() {
    /* === Central Room === */
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootTwoCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootTwoCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootTwoCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootTwoCollisionBox4").setOpacity(0);

    /* === Storage Room === */
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootTwoCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootTwoCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootTwoCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootTwoCollisionBox4").setOpacity(0);
  }

  /** Activate root three collision box in main room and storage room. */
  public static void rootThreeActivate() {
    /* === Central Room === */
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootThreeCollisionBox1").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootThreeCollisionBox2").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootThreeCollisionBox3").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootThreeCollisionBox4").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootThreeCollisionBox5").setOpacity(1);

    /* === Storage Room === */
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootThreeCollisionBox1").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootThreeCollisionBox2").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootThreeCollisionBox3").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootThreeCollisionBox4").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootThreeCollisionBox5").setOpacity(1);
  }

  /** Deactivate root three collision box in main room and storage room. */
  public static void rootThreeDeactivate() {
    /* === Central Room === */
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootThreeCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootThreeCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootThreeCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootThreeCollisionBox4").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootThreeCollisionBox5").setOpacity(0);

    /* === Storage Room === */
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootThreeCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootThreeCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootThreeCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootThreeCollisionBox4").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootThreeCollisionBox5").setOpacity(0);
  }

  /** Activate root four collision box in main room and storage room. */
  public static void rootFourActivate() {
    /* === Central Room === */
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox1").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox2").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox3").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox4").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox5").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox6").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox7").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox8").setOpacity(1);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox9").setOpacity(1);

    /* === Storage Room === */
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox1").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox2").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox3").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox4").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox5").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox6").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox7").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox8").setOpacity(1);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox9").setOpacity(1);
  }

  /** Deactivate root four collision box in main room and storage room. */
  public static void rootFourDeactivate() {
    /* === Central Room === */
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox4").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox5").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox6").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox7").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox8").setOpacity(0);
    SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#mainRootFourCollisionBox9").setOpacity(0);

    /* === Storage Room === */
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox1").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox2").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox3").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox4").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox5").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox6").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox7").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox8").setOpacity(0);
    SceneManager.getPanel(AppPanel.STORAGE).lookup("#storageRootFourCollisionBox9").setOpacity(0);
  }
}
