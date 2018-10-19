//
// Copyright Aliaksei Levin (levlam@telegram.org), Arseny Smirnov (arseny30@gmail.com) 2014-2018
//
// Distributed under the Boost Software License, Version 1.0. (See accompanying
// file LICENSE_1_0.txt or copy at http://www.boost.org/LICENSE_1_0.txt)
//
package org.drinkless.tdlib.example;

import javafx.application.Application;
import me.gjkf.bttg.BTTG;
import me.gjkf.bttg.handlers.UpdatesHandler;
import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.Log;
import org.drinkless.tdlib.TdApi;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** Example class for TDLib usage from Java. */
public final class Example {
  private static Client client = null;

  private static TdApi.AuthorizationState authorizationState = null;
  private static volatile boolean haveAuthorization = false;
  private static volatile boolean quiting = false;

  private static final Client.ResultHandler defaultHandler = new DefaultHandler();

  private static final Lock authorizationLock = new ReentrantLock();
  private static final Condition gotAuthorization = authorizationLock.newCondition();

  private static final String newLine = System.getProperty("line.separator");
  private static final String commandsLine =
      "Enter command (gcs - GetChats, gc <chatId> - GetChat, me - GetMe, sm <chatId> <message> - SendMessage, lo - LogOut, q - Quit): ";
  private static volatile String currentPrompt = null;

  static {
    System.loadLibrary("tdjni");
  }

  private static void print(String str) {
    if (currentPrompt != null) {
      System.out.println("");
    }
    System.out.println(str);
    if (currentPrompt != null) {
      System.out.print(currentPrompt);
    }
  }

//  private static void onAuthorizationStateUpdated(TdApi.AuthorizationState authorizationState) {
//    if (authorizationState != null) {
//      Example.authorizationState = authorizationState;
//    }
//    switch (Example.authorizationState.getConstructor()) {
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
//        client.send(new TdApi.SetTdlibParameters(parameters), new AuthorizationRequestHandler());
//        break;
//      case TdApi.AuthorizationStateWaitEncryptionKey.CONSTRUCTOR:
//        client.send(new TdApi.CheckDatabaseEncryptionKey(), new AuthorizationRequestHandler());
//        break;
//      case TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR:
//        {
//          String phoneNumber = promptString("Please enter phone number: ");
//          client.send(
//              new TdApi.SetAuthenticationPhoneNumber(phoneNumber, false, false),
//              new AuthorizationRequestHandler());
//          break;
//        }
//      case TdApi.AuthorizationStateWaitCode.CONSTRUCTOR:
//        {
//          String code = promptString("Please enter authentication code: ");
//          client.send(
//              new TdApi.CheckAuthenticationCode(code, "", ""), new AuthorizationRequestHandler());
//          break;
//        }
//      case TdApi.AuthorizationStateWaitPassword.CONSTRUCTOR:
//        {
//          String password = promptString("Please enter password: ");
//          client.send(
//              new TdApi.CheckAuthenticationPassword(password), new AuthorizationRequestHandler());
//          break;
//        }
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
//                  new UpdatesHandler(), null, null); // recreate client after previous has closed
//        }
//        break;
//      default:
//        System.err.println(
//            "Unsupported authorization state:" + newLine + Example.authorizationState);
//    }
//  }

  private static int toInt(String arg) {
    int result = 0;
    try {
      result = Integer.parseInt(arg);
    } catch (NumberFormatException ignored) {
    }
    return result;
  }

  private static long getChatId(String arg) {
    long chatId = 0;
    try {
      chatId = Long.parseLong(arg);
    } catch (NumberFormatException ignored) {
    }
    return chatId;
  }

  private static String promptString(String prompt) {
    System.out.print(prompt);
    currentPrompt = prompt;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String str = "";
    try {
      str = reader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    currentPrompt = null;
    return str;
  }

//  private static void getCommand() {
//    String command = promptString(commandsLine);
//    String[] commands = command.split(" ", 2);
//    try {
//      switch (commands[0]) {
//        case "gcs":
//          {
//            int limit = 20;
//            if (commands.length > 1) {
//              limit = toInt(commands[1]);
//            }
//            BTTG.getChatList(limit);
//            break;
//          }
//        case "gc":
//          client.send(new TdApi.GetChat(getChatId(commands[1])), defaultHandler);
//          break;
//        case "me":
//          client.send(new TdApi.GetMe(), defaultHandler);
//          break;
//        case "sm":
//          {
//            String[] args = commands[1].split(" ", 2);
//            sendMessage(getChatId(args[0]), args[1]);
//            break;
//          }
//        case "lo":
//          haveAuthorization = false;
//          client.send(new TdApi.LogOut(), defaultHandler);
//          break;
//        case "q":
//          quiting = true;
//          haveAuthorization = false;
//          client.send(new TdApi.Close(), defaultHandler);
//          break;
//        default:
//          System.err.println("Unsupported command: " + command);
//      }
//    } catch (ArrayIndexOutOfBoundsException e) {
//      print("Not enough arguments");
//    }
//  }

  private static void sendMessage(long chatId, String message) {
    // initialize reply markup just for testing
    TdApi.InlineKeyboardButton[] row = {
      new TdApi.InlineKeyboardButton(
          "https://telegram.org?1", new TdApi.InlineKeyboardButtonTypeUrl()),
      new TdApi.InlineKeyboardButton(
          "https://telegram.org?2", new TdApi.InlineKeyboardButtonTypeUrl()),
      new TdApi.InlineKeyboardButton(
          "https://telegram.org?3", new TdApi.InlineKeyboardButtonTypeUrl())
    };
    TdApi.ReplyMarkup replyMarkup =
        new TdApi.ReplyMarkupInlineKeyboard(new TdApi.InlineKeyboardButton[][] {row, row, row});

    TdApi.InputMessageContent content =
        new TdApi.InputMessageText(new TdApi.FormattedText(message, null), false, true);
    client.send(
        new TdApi.SendMessage(chatId, 0, false, false, replyMarkup, content), defaultHandler);
  }

  public static void main(String[] args) throws Exception {
    // TODO: possible fix for high-dpi scaling:
    // http://mail.openjdk.java.net/pipermail/openjfx-dev/2013-May/007738.html
    // TODO: read all tutorials:
    // https://docs.oracle.com/javase/8/javafx/get-started-tutorial/fxml_tutorial.htm#CHDCCHII
    // https://docs.oracle.com/javase/8/javafx/get-started-tutorial/form.htm#BABDDGEE
    // https://docs.oracle.com/javase/8/javafx/get-started-tutorial/css.htm#BEIBBBCI

    // disable TDLib log
    Log.setVerbosityLevel(3);
    if (!Log.setFilePath("tdlib.log")) {
      throw new IOError(new IOException("Write access to the current directory is required"));
    }

    // create client
    BTTG.setClient(Client.create(new UpdatesHandler(), null, null));
    Application.launch(BTTG.class, args);
    //    // test Client.execute
    //    defaultHandler.onResult(
    //        Client.execute(
    //            new TdApi.GetTextEntities(
    //                "@telegram /test_command https://telegram.org telegram.me @gif @test")));
    //
    //    // main loop
    //    while (!quiting) {
    //      // await authorization
    //      authorizationLock.lock();
    //      try {
    //        while (!haveAuthorization) {
    //          gotAuthorization.await();
    //        }
    //      } finally {
    //        authorizationLock.unlock();
    //      }
    //
    //      while (haveAuthorization) {
    //        getCommand();
    //      }
    //    }
  }

  public static class DefaultHandler implements Client.ResultHandler {
    @Override
    public void onResult(TdApi.Object object) {
      print(object.toString());
    }
  }

  public static class AuthorizationRequestHandler implements Client.ResultHandler {
    @Override
    public void onResult(TdApi.Object object) {
      switch (object.getConstructor()) {
        case TdApi.Error.CONSTRUCTOR:
          System.err.println("Receive an error:" + newLine + object);
          break;
        case TdApi.Ok.CONSTRUCTOR:
          // result is already received through UpdateAuthorizationState, nothing to do
          break;
        default:
          System.err.println("Receive wrong response from TDLib:" + newLine + object);
      }
    }
  }
}
