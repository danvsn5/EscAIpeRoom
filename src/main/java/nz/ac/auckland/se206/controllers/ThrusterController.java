package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class ThrusterController {

  @FXML private ImageView bottomLeftUnclickedPurple;
  @FXML private ImageView bottomLeftClickedPurple;
  @FXML private ImageView bottomLeftUnclickedOrange;
  @FXML private ImageView bottomLeftClickedOrange;
  @FXML private ImageView bottomLeftUnclickedBlue;
  @FXML private ImageView bottomLeftClickedBlue;
  @FXML private ImageView bottomLeftUnclickedRed;
  @FXML private ImageView bottomLeftClickedRed;

  public void setBottomLeftPurpleVisible() {
    bottomLeftUnclickedPurple.setVisible(true);
  }

  public void setBottomLeftPurpleInvisible() {
    bottomLeftUnclickedPurple.setVisible(false);
  }

  public void setBottomLeftOrangeVisible() {
    bottomLeftUnclickedOrange.setVisible(true);
  }

  public void setBottomLeftOrangeInvisible() {
    bottomLeftUnclickedOrange.setVisible(false);
  }

  public void setBottomLeftBlueVisible() {
    bottomLeftUnclickedBlue.setVisible(true);
  }

  public void setBottomLeftBlueInvisible() {
    bottomLeftUnclickedBlue.setVisible(false);
  }

  public void setBottomLeftRedVisible() {
    bottomLeftUnclickedRed.setVisible(true);
  }

  public void setBottomLeftRedInvisible() {
    bottomLeftUnclickedRed.setVisible(false);
  }
}
