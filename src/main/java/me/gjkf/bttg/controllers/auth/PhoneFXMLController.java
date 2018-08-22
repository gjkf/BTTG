package me.gjkf.bttg.controllers.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import me.gjkf.bttg.BTTG;

import java.io.IOException;
import java.util.Objects;

public class PhoneFXMLController {

  @FXML private TextField phoneNumber;

  @FXML
  protected void handleSubmitButtonAction(ActionEvent event) {
    System.out.println(phoneNumber.getText());
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