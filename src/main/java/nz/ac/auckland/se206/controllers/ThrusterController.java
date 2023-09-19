package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.ThrusterButtons.BottomRightButton;

public class ThrusterController {

  @FXML private ImageView bottomLeftUnclicked;
  @FXML private ImageView bottomLeftClicked;
  @FXML private ImageView bottomRightUnclicked;
  @FXML private ImageView bottomRightClicked;
  @FXML private ImageView topLeftUnclicked;
  @FXML private ImageView topLeftClicked;
  @FXML private ImageView topRightUnclicked;
  @FXML private ImageView topRightClicked;

  public void setBottomLeftVisible() {
    //   if (ThrusterButton.getCycleNumber() != GameState.getRandomColorNumber()) {
    //     bottomLeftUnclicked.setVisible(true);
    //   } else {
    //     ThrusterButton.timeline.pause();
    //   }
  }

  public void setBottomLeftInvisible() {
    //   bottomLeftUnclicked.setVisible(false);
  }

  public void setBottomRightInvisible() {
    bottomRightUnclicked.setVisible(false);
  }

  public void setBottomRightVisible() {
    if (BottomRightButton.getCycleNumber() != GameState.getRandomColorNumber()) {
      bottomRightUnclicked.setVisible(true);
    } else {
      BottomRightButton.timeline.pause();
    }
  }

  public void setTopLeftInvisible() {
    //   topLeftUnclicked.setVisible(false);
  }

  public void setTopLeftVisible() {
    //   if (ThrusterButton.getCycleNumber() != GameState.getRandomColorNumber()) {
    //     topLeftUnclicked.setVisible(true);
    //   } else {
    //     ThrusterButton.timeline.pause();
  }

  public void setTopRightInvisible() {
    //   topRightUnclicked.setVisible(false);
  }

  public void setTopRightVisible() {
    //   if (ThrusterButton.getCycleNumber() != GameState.getRandomColorNumber()) {
    //     topRightUnclicked.setVisible(true);
    //   } else {
    //     ThrusterButton.timeline.pause();
    //   }
  }
}
