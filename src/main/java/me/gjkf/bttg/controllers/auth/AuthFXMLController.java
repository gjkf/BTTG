package me.gjkf.bttg.controllers.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import me.gjkf.bttg.BTTG;
import me.gjkf.bttg.controllers.BTTGFXMLController;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller for the authentication code insertion.
 *
 * @see BTTGFXMLController
 */

public class AuthFXMLController{
  @FXML private TextField authCode;

  @FXML
  protected void handleSubmitButtonAction(ActionEvent event) {
    System.out.println(authCode.getText());
    try {
      authCode
          .getScene()
          .setRoot(
              FXMLLoader.load(
                  Objects.requireNonNull(
                      BTTG.class.getClassLoader().getResource("fxml/bttg.fxml"))));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}