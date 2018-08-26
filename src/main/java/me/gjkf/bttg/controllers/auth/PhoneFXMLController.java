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
 * Controller for the number insertion scene
 *
 * @see me.gjkf.bttg.controllers.BTTGFXMLController
 */
public class PhoneFXMLController {

  @FXML private TextField phoneNumber;

  @FXML
  protected void handleSubmitButtonAction(ActionEvent event) {
    BTTG.getClient()
        .send(
            new TdApi.SetAuthenticationPhoneNumber(phoneNumber.getText(), false, false),
            new AuthRequestHandler());

    try {
      phoneNumber
          .getScene()
          .setRoot(
              FXMLLoader.load(
                  Objects.requireNonNull(
                      BTTG.class.getClassLoader().getResource("fxml/auth.fxml"))));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
