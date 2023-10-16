package nz.ac.auckland.se206;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class winGame {

  public static Timeline winFlash =
      new Timeline(new KeyFrame(Duration.millis(400), e -> flashWin()));
  public static int winFlashState = 0;

  public static void flashWin() {

    if (winFlashState == 1) {
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#winGameCollisionBox").setVisible(false);
      winFlashState = 0;
    } else {
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#winGameCollisionBox").setVisible(true);
      winFlashState = 1;
    }
  }

  public static void startFlashWin() {
    // The tree changes light every 0.75s
    winFlash.setCycleCount(Timeline.INDEFINITE);
    winFlash.play();
  }
}
