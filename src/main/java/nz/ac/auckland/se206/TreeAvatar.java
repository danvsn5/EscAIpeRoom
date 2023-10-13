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

      /* =================================== Central Controller Root One ================================== */

      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootOneCollisionBox1").setOpacity(1);
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootOneCollisionBox2").setOpacity(1);

      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootOneCollisionBox3").setOpacity(1);

      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootOneCollisionBox4").setOpacity(1);

      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#miniTree").setEffect(glowBright);
      SceneManager.getPanel(AppPanel.THRUSTER).lookup("#miniTree").setEffect(glowBright);
      SceneManager.getPanel(AppPanel.CHEST).lookup("#miniTree").setEffect(glowBright);
      SceneManager.getPanel(AppPanel.STORAGE).lookup("#miniTree").setEffect(glowBright);
      // Set the tree state to 1
      treeState = 1;
    } else {
      // If the tree state is 1, indicating the tree is bright, set them to dim
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootOneCollisionBox1").setOpacity(0);
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootOneCollisionBox2").setOpacity(0);

      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootOneCollisionBox3").setOpacity(0);

      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#rootOneCollisionBox4").setOpacity(0);

      SceneManager.getPanel(AppPanel.OUTSIDE).lookup("#miniTree").setEffect(glowDim);
      SceneManager.getPanel(AppPanel.THRUSTER).lookup("#miniTree").setEffect(glowDim);
      SceneManager.getPanel(AppPanel.CHEST).lookup("#miniTree").setEffect(glowDim);
      SceneManager.getPanel(AppPanel.STORAGE).lookup("#miniTree").setEffect(glowDim);
      // Set the tree state to 0
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
}
