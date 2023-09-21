package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppPanel;
import nz.ac.auckland.se206.TreeAvatar;

public class ProgressController {

  @FXML private Rectangle topBarTop;
  @FXML private Rectangle topBarCenter;
  @FXML private Rectangle topBarBottom;
  @FXML private Label topLabel;
  @FXML private ImageView miniTree;
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

  public void goChat() {
    TreeAvatar.treeFlash.pause();
    TreeAvatar.deactivateTreeGlow();
    App.setUi(AppPanel.CHAT);
  }

  public void miniTreeGlow() {
    miniTree.setEffect(GameState.glowBright);
  }

  public void miniTreeDim() {
    miniTree.setEffect(GameState.glowDim);
  }
}
