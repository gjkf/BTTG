package me.gjkf.bttg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.gjkf.bttg.handlers.AuthCheckHandler;
import me.gjkf.bttg.handlers.AuthRequestHandler;
import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

import java.io.IOException;
import java.util.Objects;

/** Entry point of the GUI application */
public class BTTG extends Application {

  /**
   * The client object
   */
  private static Client client = null;

  /**
   * The {@code String} representation of the path for the FXML file
   */
  private static String fxml = "fxml/phone.fxml";

  @Override
  public void init() throws Exception {
    super.init();
    TdApi.TdlibParameters parameters = new TdApi.TdlibParameters();
    parameters.databaseDirectory = "tdlib";
    parameters.useMessageDatabase = true;
    parameters.useSecretChats = true;
    parameters.apiId = 303959;
    parameters.apiHash = "3d6ba7774ac56bc000ffdad76842c264";
    parameters.systemLanguageCode = "en";
    parameters.deviceModel = "Desktop";
    parameters.systemVersion = "Unknown";
    parameters.applicationVersion = "0.1";
    parameters.enableStorageOptimizer = true;

    client.send(new TdApi.SetTdlibParameters(parameters), new AuthRequestHandler());
    client.send(new TdApi.CheckDatabaseEncryptionKey(), new AuthRequestHandler());

    client.send(new TdApi.GetAuthorizationState(), new AuthCheckHandler());
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root;

    root =
        FXMLLoader.load(Objects.requireNonNull(BTTG.class.getClassLoader().getResource(getFxml())));
    Scene scene = new Scene(root, 1000, 800);
    scene
        .getStylesheets()
        .add(
            Objects.requireNonNull(
                Objects.requireNonNull(BTTG.class.getClassLoader().getResource("css/general.css"))
                    .toExternalForm()));

    primaryStage.setTitle("Better Than Telegram");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  @Override
  public void stop() throws Exception {
    super.stop();
    client.close();
  }

  public static String getFxml() {
    return fxml;
  }

  public static void setFxml(String fxml) {
    BTTG.fxml = fxml;
  }

  public static Client getClient() {
    return client;
  }

  public static void setClient(Client client) {
    BTTG.client = client;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
