package me.gjkf.bttg.controllers.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import me.gjkf.bttg.BTTG;
import me.gjkf.bttg.handlers.AuthRequestHandler;
import org.drinkless.tdlib.TdApi;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller for the authentication code insertion.
 *
 * @see me.gjkf.bttg.controllers.BTTGFXMLController
 */
public class AuthFXMLController {
  @FXML private TextField authCode;

  @FXML
  protected void handleSubmitButtonAction(ActionEvent event) {
    //TODO: handle if the user has not yet registered
    BTTG.getClient()
        .send(
            new TdApi.CheckAuthenticationCode(authCode.getText(), "Davide", "Cossu"),
            new AuthRequestHandler());

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

  @FXML
  public void goBack(ActionEvent actionEvent) {
    try {
      authCode
          .getScene()
          .setRoot(
              FXMLLoader.load(
                  Objects.requireNonNull(
                      BTTG.class.getClassLoader().getResource("fxml/phone.fxml"))));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
