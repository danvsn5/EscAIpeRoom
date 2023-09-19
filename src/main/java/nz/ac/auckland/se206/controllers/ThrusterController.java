package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class ThrusterController {

  @FXML private ImageView bottomLeftUnclickedPurple;
  @FXML private ImageView bottomLeftClickedPurple;

  public void setBottomLeftPurpleVisible() {
    bottomLeftUnclickedPurple.setVisible(true);
  }

  public void setBottomLeftPurpleInvisible() {
    bottomLeftUnclickedPurple.setVisible(false);
  }
}
