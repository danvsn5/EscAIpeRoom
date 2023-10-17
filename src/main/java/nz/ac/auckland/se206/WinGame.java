package nz.ac.auckland.se206;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import nz.ac.auckland.se206.SceneManager.AppPanel;

public class WinGame {

  public static Timeline winFlash =
      new Timeline(new KeyFrame(Duration.millis(400), e -> flashWin()));
  public static int winFlashState = 0;

  /**
   * Flashes the win game collision box on and off.
   * If winFlashState is 1, sets the winGameCollisionBox to visible and sets winFlashState to 0.
   * If winFlashState is 0, sets the winGameCollisionBox to invisible and sets winFlashState to 1.
   */
  public static void flashWin() {
    if (winFlashState == 1) { // if winFlashState is 1, set the winGameCollisionBox to visible
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#winGameCollisionBox").setOpacity(1);
      winFlashState = 0;
    } else { // if winFlashState is 0, set the winGameCollisionBox to invisible
      SceneManager.getPanel(AppPanel.MAIN_ROOM).lookup("#winGameCollisionBox").setOpacity(0);
      winFlashState = 1; 
    }
  }

  public static void startFlashWin() {
    // The tree changes light every 0.4s
    winFlash.setCycleCount(Timeline.INDEFINITE);
    winFlash.play();
  }
}
