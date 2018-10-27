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

package me.gjkf.bttg.handlers;

import me.gjkf.bttg.BTTG;
import me.gjkf.bttg.controllers.BTTGMainScene;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

/**
 * Handler for the check of the authorization. It is used on GUI startup to check what scene to
 * display.
 *
 * @see BTTG#init()
 * @author Davide Cossu
 */
public class AuthCheckHandler implements Client.ResultHandler {

  private static final Logger logger = LogManager.getLogger(AuthCheckHandler.class.getName());

  @Override
  public void onResult(TdApi.Object object) {
    switch (object.getConstructor()) {
      case TdApi.AuthorizationStateReady.CONSTRUCTOR:
        BTTG.setRoot(new BTTGMainScene());
        break;
      case TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR:
        BTTG.setFxml("fxml/phone.fxml");
        break;
      case TdApi.AuthorizationStateWaitCode.CONSTRUCTOR:
        BTTG.setFxml("fxml/auth.fxml");
        break;
      default:
        logger.warn("Unrecognized constructor:\n{}", object);
    }
  }
}
