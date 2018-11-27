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

package me.gjkf.bttg;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.gjkf.bttg.controllers.BTTGMainScene;
import me.gjkf.bttg.handlers.AuthCheckHandler;
import me.gjkf.bttg.handlers.AuthRequestHandler;
import me.gjkf.bttg.util.ChatInfo;
import me.gjkf.bttg.util.OrderedChat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Entry point of the GUI application.
 *
 * <p>Provides methods to locally store messages and chats so that it is easier to work with them.
 *
 * @author Davide Cossu
 */
public class BTTG extends Application {

  private static final Logger logger = LogManager.getLogger(BTTG.class.getName());

  /**
   * The client object
   */
  private static Client client = null;

  /** The {@code String} representation of the path for the FXML file */
  private static String fxml = null;

  private static Parent root;

  private static final Map<Integer, TdApi.User> users = new ConcurrentHashMap<>();
  private static final Map<Integer, TdApi.BasicGroup> basicGroups = new ConcurrentHashMap<>();
  private static final Map<Integer, TdApi.Supergroup> superGroups = new ConcurrentHashMap<>();
  private static final Map<Integer, TdApi.SecretChat> secretChats = new ConcurrentHashMap<>();
  private static final Map<Long, TdApi.Messages> messages = new ConcurrentHashMap<>();
  private static final Map<Long, TdApi.Hashtags> hashtags = new ConcurrentHashMap<>();

  private static final NavigableSet<OrderedChat> chatList = new TreeSet<>();
  private static final ConcurrentMap<Long, TdApi.Chat> chats = new ConcurrentHashMap<>();
  private static boolean haveFullChatList = false;

  private static final Map<Integer, TdApi.UserFullInfo> usersFullInfo = new ConcurrentHashMap<>();
  private static final Map<Integer, TdApi.BasicGroupFullInfo> basicGroupsFullInfo =
      new ConcurrentHashMap<>();
  private static final Map<Integer, TdApi.SupergroupFullInfo> superGroupsFullInfo =
      new ConcurrentHashMap<>();

  private static final CountDownLatch latch = new CountDownLatch(1);

  /** {@inheritDoc} */
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
    parameters.useChatInfoDatabase = true;

    client.send(new TdApi.SetTdlibParameters(parameters), new AuthRequestHandler());
    client.send(new TdApi.CheckDatabaseEncryptionKey(), new AuthRequestHandler());

    client.send(new TdApi.GetAuthorizationState(), new AuthCheckHandler());
    getChatList(Integer.MAX_VALUE);
    getChats().forEach((id, chat) -> ChatInfo.retrieveMessages(id, Integer.MAX_VALUE));
  }

  /** {@inheritDoc} */
  @Override
  public void start(Stage primaryStage) {
    //        try {
    //          root =
    //
    //
    //
    // FXMLLoader.load(Objects.requireNonNull(BTTG.class.getClassLoader().getResource(getFxml())));
    //        } catch (IOException e) {
    //          e.printStackTrace();
    //        }
    try {
      latch.await(1500, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      logger.throwing(e);
    }
    root = new BTTGMainScene();
    Scene scene = new Scene(root, 1500, 1300);
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

  /** {@inheritDoc} */
  @Override
  public void stop() throws Exception {
    super.stop();
    client.close();
  }

  /**
   * Getter for property 'fxml'.
   *
   * @return Value for property 'fxml'.
   */
  public static String getFxml() {
    return fxml;
  }

  /**
   * Setter for property 'fxml'.
   *
   * @param fxml Value to set for property 'fxml'.
   */
  public static void setFxml(String fxml) {
    BTTG.fxml = fxml;
  }

  /**
   * Getter for property 'client'.
   *
   * @return Value for property 'client'.
   */
  public static Client getClient() {
    return client;
  }

  /**
   * Setter for property 'client'.
   *
   * @param client Value to set for property 'client'.
   */
  public static void setClient(Client client) {
    BTTG.client = client;
  }

  /**
   * Getter for property 'root'.
   *
   * @return Value for property 'root'.
   */
  public static Parent getRoot() {
    return root;
  }

  /**
   * Setter for property 'root'.
   *
   * @param root Value to set for property 'root'.
   */
  public static void setRoot(Parent root) {
    BTTG.root = root;
  }

  public static void setChatOrder(TdApi.Chat chat, long order) {
    synchronized (chatList) {
      if (chat.order != 0) {
        boolean isRemoved = chatList.remove(new OrderedChat(chat.order, chat.id));
        assert isRemoved;
      }

      chat.order = order;

      if (chat.order != 0) {
        boolean isAdded = chatList.add(new OrderedChat(chat.order, chat.id));
        assert isAdded;
      }
    }
  }

  /**
   * Fills {@link #chatList} with {@code limit} chats.
   *
   * @param limit The maximum amount of chats allowed.
   */
  public static void getChatList(int limit) {
    synchronized (chatList) {
      if (!haveFullChatList && limit > chatList.size()) {
        // have enough chats in the chat list or chat list is too small
        long offsetOrder = Long.MAX_VALUE;
        long offsetChatId = 0;
        if (!chatList.isEmpty()) {
          OrderedChat last = chatList.last();
          offsetOrder = last.order;
          offsetChatId = last.chatId;
        }
        client.send(
            new TdApi.GetChats(offsetOrder, offsetChatId, limit - chatList.size()),
            object -> {
              switch (object.getConstructor()) {
                case TdApi.Error.CONSTRUCTOR:
                  logger.error("Received an error from GetChats:\n{}", object);
                  break;
                case TdApi.Chats.CONSTRUCTOR:
                  long[] chatIds = ((TdApi.Chats) object).chatIds;
                  if (chatIds.length == 0) {
                    synchronized (chatList) {
                      haveFullChatList = true;
                    }
                  }
                  // chats had already been received through updates, let's retry request
                  getChatList(limit);
                  break;
                default:
                  logger.warn("Received unknown constructor for GetChats. Object:\n{}", object);
              }
            });
        return;
      }

      synchronized (chatList) {
        haveFullChatList = true;
        latch.countDown();
      }
    }
  }

  /**
   * Getter for property 'chats'.
   *
   * @return Value for property 'chats'.
   */
  public static Map<Long, TdApi.Chat> getChats() {
    return chats;
  }

  /**
   * Getter for property 'users'.
   *
   * @return Value for property 'users'.
   */
  public static Map<Integer, TdApi.User> getUsers() {
    return users;
  }

  /**
   * Getter for property 'basicGroups'.
   *
   * @return Value for property 'basicGroups'.
   */
  public static Map<Integer, TdApi.BasicGroup> getBasicGroups() {
    return basicGroups;
  }

  /**
   * Getter for property 'superGroups'.
   *
   * @return Value for property 'superGroups'.
   */
  public static Map<Integer, TdApi.Supergroup> getSuperGroups() {
    return superGroups;
  }

  /**
   * Getter for property 'secretChats'.
   *
   * @return Value for property 'secretChats'.
   */
  public static Map<Integer, TdApi.SecretChat> getSecretChats() {
    return secretChats;
  }

  /**
   * Getter for property 'chatList'.
   *
   * @return Value for property 'chatList'.
   */
  public static SortedSet<OrderedChat> getChatList() {
    return chatList;
  }

  /**
   * Getter for property 'haveFullChatList'.
   *
   * @return Value for property 'haveFullChatList'.
   */
  public static boolean isHaveFullChatList() {
    return haveFullChatList;
  }

  /**
   * Setter for property 'haveFullChatList'.
   *
   * @param haveFullChatList Value to set for property 'haveFullChatList'.
   */
  public static void setHaveFullChatList(boolean haveFullChatList) {
    BTTG.haveFullChatList = haveFullChatList;
  }

  /**
   * Getter for property 'usersFullInfo'.
   *
   * @return Value for property 'usersFullInfo'.
   */
  public static Map<Integer, TdApi.UserFullInfo> getUsersFullInfo() {
    return usersFullInfo;
  }

  /**
   * Getter for property 'basicGroupsFullInfo'.
   *
   * @return Value for property 'basicGroupsFullInfo'.
   */
  public static Map<Integer, TdApi.BasicGroupFullInfo> getBasicGroupsFullInfo() {
    return basicGroupsFullInfo;
  }

  /**
   * Getter for property 'superGroupsFullInfo'.
   *
   * @return Value for property 'superGroupsFullInfo'.
   */
  public static Map<Integer, TdApi.SupergroupFullInfo> getSuperGroupsFullInfo() {
    return superGroupsFullInfo;
  }

  /**
   * Getter for property 'messages'.
   *
   * @return Value for property 'messages'.
   */
  public static Map<Long, TdApi.Messages> getMessages() {
    return messages;
  }

  /**
   * Getter for property 'hashtags'.
   *
   * @return Value for property 'hashtags'.
   */
  public static Map<Long, TdApi.Hashtags> getHashtags() {
    return hashtags;
  }
}
