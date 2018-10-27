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
 * Controller for the number insertion scene.
 *
 * @author Davide Cossu
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
