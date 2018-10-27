/*
 * BTTG: a Telegram client for those who want more.
 * Copyright (C) 2018  Davide Cossu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
 * @author Davide Cossu
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
