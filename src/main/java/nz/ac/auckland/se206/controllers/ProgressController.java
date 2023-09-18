package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import nz.ac.SceneManager;
import nz.ac.auckland.se206.App;

public class ProgressController {

  @FXML private Rectangle topBarTop;
  @FXML private Rectangle topBarCenter;
  @FXML private Rectangle topBarBottom;
  @FXML private Label topLabel;

  @FXML private Rectangle bottomBarTop;
  @FXML private Rectangle bottomBarCenter;
  @FXML private Rectangle bottomBarBottom;
  @FXML private Label bottomLabel;

  @FXML private Button returnButton;

  public void initialize() {
    // checks which tasks are active due to the randomiser in launch controller. Depending on which
    // tasks are active, the text will be updated
  }

  public void returnPreviousPanel() {
    App.setUi(SceneManager.getPrevious());
  }

  // once merged, Mission manager will be used to access the percentage of each task, and will
  // thereby be used to update the visuals of the progress bar.
}
