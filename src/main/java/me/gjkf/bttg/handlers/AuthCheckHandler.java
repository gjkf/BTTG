package me.gjkf.bttg.handlers;

import me.gjkf.bttg.BTTG;
import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

/**
 * Handler for the check of the authorization. It is used on GUI startup to check what scene to
 * display.
 *
 * @see BTTG#init()
 */
public class AuthCheckHandler implements Client.ResultHandler {
  @Override
  public void onResult(TdApi.Object object) {
    System.out.println(object);
    switch (object.getConstructor()) {
      case TdApi.AuthorizationStateReady.CONSTRUCTOR:
        BTTG.setFxml("fxml/bttg.fxml");
        break;
      case TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR:
        BTTG.setFxml("fxml/phone.fxml");
        break;
      case TdApi.AuthorizationStateWaitCode.CONSTRUCTOR:
        BTTG.setFxml("fxml/auth.fxml");
        break;
      default:
        System.err.println("Unrecognized constructor:" + System.lineSeparator() + object);
    }
  }
}
