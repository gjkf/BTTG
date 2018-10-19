package me.gjkf.bttg.handlers;

import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

/**
 * Handles the request for authorization.
 */

public class AuthRequestHandler implements Client.ResultHandler {
  @Override
  public void onResult(TdApi.Object object) {
    switch (object.getConstructor()) {
      case TdApi.Error.CONSTRUCTOR:
        System.out.println("Receive an error:" + System.getProperty("line.separator") + object);
        break;
      case TdApi.Ok.CONSTRUCTOR:
        // result is already received through UpdateAuthorizationState, nothing to do
        break;
      default:
        System.out.println("Receive wrong response from TDLib:" + System.getProperty("line" +
            ".separator") + object);
    }
  }
}
