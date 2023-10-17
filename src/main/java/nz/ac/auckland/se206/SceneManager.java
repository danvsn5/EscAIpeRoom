package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

/** This class manages all scenes in the game. */
public class SceneManager {

  /** Add enums for all panels here. */
  public enum AppPanel {
    LAUNCH,
    MAIN_ROOM,
    OUTSIDE,
    CHAT,
    LOSE,
    WIN,
    THRUSTER,
    CHEST,
    PROGRESS,
    PREVIOUS,
    STORAGE
  }

  // Create a hashmap to store all panels in the game
  private static HashMap<AppPanel, Parent> sceneMap = new HashMap<AppPanel, Parent>();
  public static AppPanel previous;

  /**
   * Add a new panel to hashmap, using AppPanel enum as key.
   *
   * @param panel the panel that needs to be added.
   * @param parent the parent of the panel.
   */
  public static void addPanel(AppPanel panel, Parent parent) {
    sceneMap.put(panel, parent);
  }

  /**
   * Record the previous panel. 
   * This method is used when the player enters a new panel.
   *
   * @param previous the previous panel.
   */
  public static void setPrevious(AppPanel previous) {
    SceneManager.previous = previous;
  }

  /**
   * Get the AppPanel enum of the previous panel.
   *
   * @return the AppPanel enum of the previous panel.
   */
  public static AppPanel getPrevious() {
    return previous;
  }

  /**
   * Get the Parent object using the input AppPanel key.
   *
   * @param panel The AppPanel enum as the key of hashmap.
   * @return the Parent found using AppPanel key.
   */
  public static Parent getPanel(AppPanel panel) {
    return sceneMap.get(panel);
  }

  /** Clear all panels in the map for a new game. */
  public static void clearMap() {
    sceneMap.clear();
  }

  /**
   * Displays a dialog box with the given title, header text, and message.
   *
   * @param title the title of the dialog box.
   * @param headerText the header text of the dialog box.
   * @param message the message content of the dialog box.
   */
  public static void showDialog(String title, String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
