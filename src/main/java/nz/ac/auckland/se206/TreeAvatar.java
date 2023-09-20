package nz.ac.auckland.se206;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.Glow;
import javafx.util.Duration;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class TreeAvatar {

  public static Timeline treeFlash =
      new Timeline(new KeyFrame(Duration.seconds(1), e -> flashTree()));
  private static int treeState = 0;
  private static Glow glowDim = new Glow(0.0);
  private static Glow glowBright = new Glow(0.7);

  public static void startFlashTree() {
    treeFlash.setCycleCount(Timeline.INDEFINITE);
    treeFlash.play();
  }

  public static void flashTree() {
    if (treeState == 0) {
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#miniTree").setEffect(glowBright);
      treeState = 1;
    } else {
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#miniTree").setEffect(glowDim);
      treeState = 0;
    }
  }
}
