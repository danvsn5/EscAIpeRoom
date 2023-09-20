package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;

public class SceneManager {

  public enum AppPanel {
    LAUNCH,
    MAIN_ROOM,
    OUTSIDE,
    WORK,
    CHAT,
    LOSE,
    WIN,
    CHEST,
    PROGRESS,
    PREVIOUS
  }

  private static HashMap<AppPanel, Parent> sceneMap = new HashMap<AppPanel, Parent>();

  public static AppPanel previous;

  public static void addPanel(AppPanel panel, Parent parent) {
    sceneMap.put(panel, parent);
  }

  public static void setPrevious(AppPanel previous) {
    SceneManager.previous = previous;
  }

  public static AppPanel getPrevious() {
    return previous;
  }

  public static Parent getPanel(AppPanel panel) {
    return sceneMap.get(panel);
  }

  public static void clearMap() {
    sceneMap.clear();
  }
}
