package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.ThrusterButtons;

public class ThrusterController {

  @FXML private ImageView bottomLeftUnclicked;
  @FXML private ImageView bottomLeftClicked;

  public void setBottomLeftVisible() {
    if (ThrusterButtons.getCycleNumber() != GameState.getRandomColorNumber()) {
      bottomLeftUnclicked.setVisible(true);
    } else {
      ThrusterButtons.timeline.pause();
    }
  }

  public void setBottomLeftInvisible() {
    bottomLeftUnclicked.setVisible(false);
  }
}
