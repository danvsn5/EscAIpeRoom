package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

public class SceneManager {

  public enum AppPanel {
    LAUNCH,
    MAIN_ROOM,
    OUTSIDE,
    WORK,
    CHAT,
    LOSE,
    WIN,
    THRUSTER,
    CHEST,
    PROGRESS,
    PREVIOUS,
    STORAGE
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

  /**
   * Displays a dialog box with the given title, header text, and message.
   *
   * @param title the title of the dialog box
   * @param headerText the header text of the dialog box
   * @param message the message content of the dialog box
   */
  public static void showDialog(String title, String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
