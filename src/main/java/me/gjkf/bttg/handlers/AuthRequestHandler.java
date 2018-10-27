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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

/**
 * Handles the request for authorization.
 *
 * @author Davide Cossu
 */

public class AuthRequestHandler implements Client.ResultHandler {

  private static final Logger logger = LogManager.getLogger(AuthRequestHandler.class.getName());

  @Override
  public void onResult(TdApi.Object object) {
    switch (object.getConstructor()) {
      case TdApi.Error.CONSTRUCTOR:
        logger.error("Received an error:\n{}", object);
        break;
      case TdApi.Ok.CONSTRUCTOR:
        // result is already received through UpdateAuthorizationState, nothing to do
        break;
      default:
        logger.warn("Unrecognized constructor:\n{}", object);
    }
  }
}
