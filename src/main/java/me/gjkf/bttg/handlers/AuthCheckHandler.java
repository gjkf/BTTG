package me.gjkf.bttg.handlers;

import me.gjkf.bttg.BTTG;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

/**
 * Handler for the check of the authorization. It is used on GUI startup to check what scene to
 * display.
 *
 * @see BTTG#init()
 */
public class AuthCheckHandler implements Client.ResultHandler {

  private static final Logger logger = LogManager.getLogger(AuthCheckHandler.class.getName());

  @Override
  public void onResult(TdApi.Object object) {
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
        logger.warn("Unrecognized constructor:\n{}", object);
    }
  }
}
