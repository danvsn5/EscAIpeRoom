package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/**
 * The ProgressController class is responsible for controlling 
 * the progress UI of the game.
 */
public class ProgressController {

  @FXML private Rectangle topBarTop;
  @FXML private Rectangle topBarCenter;
  @FXML private Rectangle topBarBottom;
  @FXML private Label topLabel;
  @FXML private Rectangle bottomBarTop;
  @FXML private Rectangle bottomBarCenter;
  @FXML private Rectangle bottomBarBottom;
  @FXML private Label bottomLabel;
  @FXML private Label counter;
  @FXML private Button returnButton;

  public void initialize() {
    // checks which tasks are active due to the randomiser in launch controller. Depending on which
    // tasks are active, the text will be updated
  }

  public void returnPreviousPanel() {
    App.setUi(SceneManager.getPrevious());
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   * @throws IOException throws an exception if the previous scene cannot be found
   * @throws ApiProxyException throws an exception if the API cannot be accessed
   */
  @FXML
  public void onKeyPressed(KeyEvent event) throws ApiProxyException, IOException {
    if (event.getCode().toString().equals("ESCAPE")) {
      App.setUi(SceneManager.getPrevious());
    }
  }
}
