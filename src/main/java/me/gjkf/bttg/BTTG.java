package me.gjkf.bttg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

import java.io.IOException;
import java.util.Objects;

/** Entry point of the GUI application */
public class BTTG extends Application {

  private static Client client = null;

  private static TdApi.AuthorizationState authorizationState = null;
  private static volatile boolean haveAuthorization = false;
  private static volatile boolean quiting = false;

  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root;

    root =
        FXMLLoader.load(
            Objects.requireNonNull(BTTG.class.getClassLoader().getResource("fxml/phone.fxml")));

    Scene scene = new Scene(root, 1000, 800);

    primaryStage.setTitle("Better Than Telegram");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  //  private static void onAuthorizationStateUpdated(TdApi.AuthorizationState authorizationState) {
  //    if (authorizationState != null) {
  //      BTTG.authorizationState = authorizationState;
  //    }
  //    switch (BTTG.authorizationState.getConstructor()) {
  //      case TdApi.AuthorizationStateWaitTdlibParameters.CONSTRUCTOR:
  //        TdApi.TdlibParameters parameters = new TdApi.TdlibParameters();
  //        parameters.databaseDirectory = "tdlib";
  //        parameters.useMessageDatabase = true;
  //        parameters.useSecretChats = true;
  //        parameters.apiId = 303959;
  //        parameters.apiHash = "3d6ba7774ac56bc000ffdad76842c264";
  //        parameters.systemLanguageCode = "en";
  //        parameters.deviceModel = "Desktop";
  //        parameters.systemVersion = "Unknown";
  //        parameters.applicationVersion = "1.0";
  //        parameters.enableStorageOptimizer = true;
  //
  //        client.send(new TdApi.SetTdlibParameters(parameters), new
  // Example.AuthorizationRequestHandler());
  //        break;
  //      case TdApi.AuthorizationStateWaitEncryptionKey.CONSTRUCTOR:
  //        client.send(new TdApi.CheckDatabaseEncryptionKey(), new
  // Example.AuthorizationRequestHandler());
  //        break;
  //      case TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR:
  //      {
  //        String phoneNumber = promptString("Please enter phone number: ");
  //        client.send(
  //            new TdApi.SetAuthenticationPhoneNumber(phoneNumber, false, false),
  //            new Example.AuthorizationRequestHandler());
  //        break;
  //      }
  //      case TdApi.AuthorizationStateWaitCode.CONSTRUCTOR:
  //      {
  //        String code = promptString("Please enter authentication code: ");
  //        client.send(
  //            new TdApi.CheckAuthenticationCode(code, "", ""), new
  // Example.AuthorizationRequestHandler());
  //        break;
  //      }
  //      case TdApi.AuthorizationStateWaitPassword.CONSTRUCTOR:
  //      {
  //        String password = promptString("Please enter password: ");
  //        client.send(
  //            new TdApi.CheckAuthenticationPassword(password), new
  // Example.AuthorizationRequestHandler());
  //        break;
  //      }
  //      case TdApi.AuthorizationStateReady.CONSTRUCTOR:
  //        haveAuthorization = true;
  //        authorizationLock.lock();
  //        try {
  //          gotAuthorization.signal();
  //        } finally {
  //          authorizationLock.unlock();
  //        }
  //        break;
  //      case TdApi.AuthorizationStateLoggingOut.CONSTRUCTOR:
  //        haveAuthorization = false;
  //        print("Logging out");
  //        break;
  //      case TdApi.AuthorizationStateClosing.CONSTRUCTOR:
  //        haveAuthorization = false;
  //        print("Closing");
  //        break;
  //      case TdApi.AuthorizationStateClosed.CONSTRUCTOR:
  //        print("Closed");
  //        if (!quiting) {
  //          client =
  //              Client.create(
  //                  new UpdatesHandler(), null, null); // recreate client after previous has
  // closed
  //        }
  //        break;
  //      default:
  //        System.err.println(
  //            "Unsupported authorization state:" + newLine + Example.authorizationState);
  //    }
  //  }

  public static void main(String[] args) {
    launch(args);
  }
}
