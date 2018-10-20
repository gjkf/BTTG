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

package me.gjkf.bttg.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import me.gjkf.bttg.BTTG;
import me.gjkf.bttg.controls.ChatControl;
import me.gjkf.bttg.controls.ChatItem;
import me.gjkf.bttg.util.OrderedChat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drinkless.tdlib.TdApi;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class BTTGMainScene extends StackPane {

  private static final Logger logger = LogManager.getLogger(BTTGMainScene.class.getName());

  public BTTGMainScene() {
    super();

    setAlignment(Pos.CENTER);
    setPadding(new Insets(50));
    setScaleX(2);
    setScaleY(2);

    setPrefWidth(1000);
    setPrefHeight(1000);

    HBox hBox = new HBox();
    hBox.setAlignment(Pos.CENTER);
    hBox.setSpacing(10);

    ChatControl control = new ChatControl();
    for (OrderedChat chat : BTTG.getChatList()) {
      control.getChildren().add(new ChatItem(chat.chatId));
    }

    ScrollPane scrollPane = new ScrollPane(control);
    scrollPane.setMaxHeight(150);
    scrollPane.setPrefWidth(150);
    scrollPane.setFitToHeight(true);

    VBox messageBox = new VBox();
    messageBox.setAlignment(Pos.CENTER);
    messageBox.setSpacing(10);

    TextField messageText = new TextField();
    messageBox.getChildren().add(messageText);

    Button sendMessage = new Button("Send message");
    sendMessage.setOnMousePressed(
        event -> {
          // Create the message object
          TdApi.InputMessageContent content =
              new TdApi.InputMessageText(
                  new TdApi.FormattedText(messageText.getText(), null), false, true);
          control
              .getSelected()
              .forEach(
                  chatItem -> {
                    // [0] = chatId as integer, [1] = constructor ID
                    int[] ret = {0, 1};
                    CountDownLatch latch = new CountDownLatch(1);
                    BTTG.getClient()
                        .send(
                            new TdApi.GetChat(chatItem.getChatId()),
                            result -> {
                              switch (((TdApi.Chat) result).type.getConstructor()) {
                                case TdApi.ChatTypePrivate.CONSTRUCTOR:
                                  ret[0] = (int) chatItem.getChatId();
                                  ret[1] = TdApi.ChatTypePrivate.CONSTRUCTOR;
                                  latch.countDown();
                                  break;
                                case TdApi.ChatTypeSupergroup.CONSTRUCTOR:
                                  int superGroupId =
                                      (((TdApi.ChatTypeSupergroup) ((TdApi.Chat) result).type))
                                          .supergroupId;
                                  ret[0] = superGroupId;
                                  ret[1] = TdApi.ChatTypeSupergroup.CONSTRUCTOR;
                                  latch.countDown();
                                  break;
                                case TdApi.ChatTypeBasicGroup.CONSTRUCTOR:
                                  int basicGroupId =
                                      (((TdApi.ChatTypeBasicGroup) ((TdApi.Chat) result).type))
                                          .basicGroupId;
                                  ret[0] = basicGroupId;
                                  ret[1] = TdApi.ChatTypeBasicGroup.CONSTRUCTOR;
                                  latch.countDown();
                                  break;
                                case TdApi.ChatTypeSecret.CONSTRUCTOR:
                                  int secretChatId =
                                      (((TdApi.ChatTypeSecret) ((TdApi.Chat) result).type))
                                          .secretChatId;
                                  ret[0] = secretChatId;
                                  ret[1] = TdApi.ChatTypeSecret.CONSTRUCTOR;
                                  latch.countDown();
                              }
                            });
                    try {
                      latch.await(150, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                      logger.throwing(e);
                    }

                    // The chatId is different from the one that Td uses to retrieve a
                    // conversation. That means we have to convert the long chatId to an integer
                    // version using the Td methods.

                    switch (ret[1]) {
                      case TdApi.ChatTypePrivate.CONSTRUCTOR:
                        BTTG.getClient()
                            .send(
                                new TdApi.CreatePrivateChat(ret[0], true),
                                logger::info);
                        break;
                      case TdApi.ChatTypeSupergroup.CONSTRUCTOR:
                        BTTG.getClient()
                            .send(
                                new TdApi.CreateSupergroupChat(ret[0], false),
                                logger::info);
                        break;
                      case TdApi.ChatTypeBasicGroup.CONSTRUCTOR:
                        BTTG.getClient()
                            .send(
                                new TdApi.CreateBasicGroupChat(ret[0], true),
                                logger::info);
                        break;
                      case TdApi.ChatTypeSecret.CONSTRUCTOR:
                        BTTG.getClient()
                            .send(new TdApi.CreateSecretChat(ret[0]), logger::info);
                        break;
                      default:
                        logger.warn("Constructor {} not recognized.", ret[1]);
                    }

                    BTTG.getClient()
                        .send(
                            new TdApi.SendMessage(
                                chatItem.getChatId(), 0, false, false, null, content),
                            logger::info);
                  });
        });
    messageBox.getChildren().add(sendMessage);

    hBox.getChildren().addAll(scrollPane, messageBox);

    Pane p = new Pane(hBox);

    HBox h = new HBox();
    h.setPrefSize(1000, 1000);
    h.setAlignment(Pos.CENTER);

    VBox v = new VBox();
    v.setPrefSize(p.getPrefWidth(), h.getPrefHeight());
    v.setAlignment(Pos.CENTER);

    v.getChildren().add(p);
    h.getChildren().add(v);

    getChildren().add(h);
  }
}
