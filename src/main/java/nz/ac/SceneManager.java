package nz.ac;

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
    WIN
  }

  private static HashMap<AppPanel, Parent> sceneMap = new HashMap<AppPanel, Parent>();

  public static void addPanel(AppPanel panel, Parent parent) {
    sceneMap.put(panel, parent);
  }

  public static Parent getPanel(AppPanel panel) {
    return sceneMap.get(panel);
  }

  public static void clearMap() {
    sceneMap.clear();
  }
}
