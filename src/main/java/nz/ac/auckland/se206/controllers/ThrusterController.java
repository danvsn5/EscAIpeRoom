package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class ThrusterController {

  @FXML private ImageView bottomLeftUnclicked;
  @FXML private ImageView bottomLeftClicked;

  public void setBottomLeftVisible() {
    bottomLeftUnclicked.setVisible(true);
  }

  public void setBottomLeftInvisible() {
    bottomLeftUnclicked.setVisible(false);
  }
}
