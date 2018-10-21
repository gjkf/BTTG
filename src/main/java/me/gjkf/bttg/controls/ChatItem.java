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

package me.gjkf.bttg.controls;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Label;
import me.gjkf.bttg.BTTG;
import me.gjkf.bttg.controllers.BTTGMainScene;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drinkless.tdlib.TdApi;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Represents the chat in the {@link ChatListControl}.
 */
public class ChatItem extends Label {

  private static final Logger logger = LogManager.getLogger(ChatItem.class.getName());

  private final long chatId;

  public ChatItem(long chatId) {
    this.chatId = chatId;
    initialize();
  }

  /** Initialize the component, sets its position and class, sets the text. */
  private void initialize() {
    getStyleClass().add("chatItem");
    setAccessibleRole(AccessibleRole.LIST_ITEM);
    setAlignment(Pos.CENTER);
    setMinSize(100, 20);
    if (BTTG.getChats().containsKey(chatId)) {
      setText(String.valueOf(BTTG.getChats().get(chatId).title));
    }

    setOnMouseClicked(
        event -> {
          setSelected(!getSelected());
          if (getSelected()) {
            // We need to call multiple times the GetChatHistory function since it will retrieve
            // different amount of messages up to 100 (or a set limit). To optimize it all, it will
            // just fetch some, not all of them.
            long from = 0;
            int total = 100;
            CountDownLatch latch = new CountDownLatch(total);
            while (total > 0) {
              int finalTotal = total;
              BTTG.getClient()
                  .send(
                      new TdApi.GetChatHistory(chatId, from, 0, total, false),
                      result -> {
                        switch (result.getConstructor()) {
                          case TdApi.Messages.CONSTRUCTOR:
                            BTTG.getMessages()
                                .merge(
                                    chatId,
                                    (TdApi.Messages) result,
                                    (o, n) ->
                                        new TdApi.Messages(
                                            o.totalCount + n.totalCount,
                                            Stream.concat(
                                                Arrays.stream(o.messages),
                                                Arrays.stream(n.messages))
                                                .toArray(TdApi.Message[]::new)));
                            TdApi.Message[] messages = ((TdApi.Messages) result).messages;

                            logger.info(
                                "{}={}/{}",
                                chatId,
                                BTTG.getMessages().get(chatId).totalCount,
                                finalTotal);

                            for (TdApi.Message message : messages) {

                              TdApi.MessageContent content = message.content;
                              // TODO: 10/20/18 Fill in the ifs with relevant controls and handles
                              if (content instanceof TdApi.MessageText) {

                              } else if (content instanceof TdApi.MessageAudio) {

                              } else if (content instanceof TdApi.MessageAnimation) {

                              } else if (content instanceof TdApi.MessageBasicGroupChatCreate) {

                              } else if (content instanceof TdApi.MessageCall) {

                              } else if (content instanceof TdApi.MessageChatAddMembers) {

                              } else if (content instanceof TdApi.MessageChatChangePhoto) {

                              } else if (content instanceof TdApi.MessageChatChangeTitle) {

                              } else if (content instanceof TdApi.MessageChatDeleteMember) {

                              } else if (content instanceof TdApi.MessageChatDeletePhoto) {

                              } else if (content instanceof TdApi.MessageChatJoinByLink) {

                              } else if (content instanceof TdApi.MessageChatSetTtl) {

                              } else if (content instanceof TdApi.MessageChatUpgradeFrom) {

                              } else if (content instanceof TdApi.MessageChatUpgradeTo) {

                              } else if (content instanceof TdApi.MessageContact) {

                              } else if (content instanceof TdApi.MessageContactRegistered) {

                              } else if (content instanceof TdApi.MessageCustomServiceAction) {

                              } else if (content instanceof TdApi.MessageDocument) {

                              } else if (content instanceof TdApi.MessageExpiredPhoto) {

                              } else if (content instanceof TdApi.MessageExpiredVideo) {

                              } else if (content instanceof TdApi.MessageGame) {

                              } else if (content instanceof TdApi.MessageGameScore) {

                              } else if (content instanceof TdApi.MessageInvoice) {

                              } else if (content instanceof TdApi.MessageLocation) {

                              } else if (content instanceof TdApi.MessagePaymentSuccessful) {

                              } else if (content instanceof TdApi.MessagePaymentSuccessfulBot) {

                              } else if (content instanceof TdApi.MessagePhoto) {

                              } else if (content instanceof TdApi.MessagePinMessage) {

                              } else if (content instanceof TdApi.MessageScreenshotTaken) {

                              } else if (content instanceof TdApi.MessageSticker) {

                              } else if (content instanceof TdApi.MessageSupergroupChatCreate) {

                              } else if (content instanceof TdApi.MessageVenue) {

                              } else if (content instanceof TdApi.MessageVideo) {

                              } else if (content instanceof TdApi.MessageVideoNote) {

                              } else if (content instanceof TdApi.MessageVoiceNote) {

                              } else if (content instanceof TdApi.MessageWebsiteConnected) {

                              } else if (content instanceof TdApi.MessageUnsupported) {

                              }
                            }
                            latch.countDown();
                            break;
                          default:
                            logger.warn("Unrecognized constructor:\n{}", result);
                            break;
                        }
                      });
              try {
                latch.await(150, TimeUnit.MILLISECONDS);
              } catch (InterruptedException e) {
                logger.throwing(e);
              }
              from = Arrays.stream(BTTG.getMessages().get(chatId).messages).findFirst().get().id;
              total -= BTTG.getMessages().get(chatId).totalCount;
            }

            ((BTTGMainScene) BTTG.getRoot()).chat.getChildren().clear();

            BTTG.getMessages()
                .forEach(
                    (chat, content) -> {
                      if (chat == chatId) {
                        Stream.of(content.messages)
                            .forEach(
                                message ->
                                    ((BTTGMainScene) BTTG.getRoot())
                                        .chat
                                        .getChildren()
                                        .add(
                                            0,
                                            new MessageItem(
                                                chat.toString(),
                                                ((TdApi.MessageText) message.content).text.text)));
                      }
                    });
          } else {
            ((BTTGMainScene) BTTG.getRoot()).chat.getChildren().clear();
          }
        });
  }

  public long getChatId() {
    return chatId;
  }

  public void setSelected(boolean selected) {
    this.selected.set(selected);
  }

  public boolean getSelected() {
    return selected.get();
  }

  private final BooleanProperty selected =
      new BooleanPropertyBase(false) {
        @Override
        protected void invalidated() {
          pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, get());
        }

        @Override
        public Object getBean() {
          return ChatItem.this;
        }

        @Override
        public String getName() {
          return "selected";
        }
      };

  private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");
}
