package me.gjkf.bttg.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

/**
 * Handles the request for authorization.
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
