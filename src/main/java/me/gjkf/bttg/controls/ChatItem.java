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
import me.gjkf.bttg.util.ChatInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Stream;

/**
 * Represents the chat in the {@link ChatListControl}.
 *
 * <p>On click, it will retrieve the last 100 messages from the chat with id {@link ChatItem#chatId}
 * and create the corresponding {@link MessageItem} objects to display in the {@link
 * BTTGMainScene#chat}.
 *
 * @see ChatControl
 * @see MessageItem
 * @author Davide Cossu
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
            ChatInfo.retrieveMessages(chatId, 100);

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
                                        .add(0, new MessageItem(chat, message.id)));
                      }
                    });
          } else {
            ((BTTGMainScene) BTTG.getRoot()).chat.getChildren().clear();
          }
        });
  }

  /**
   * Getter for property 'chatId'.
   *
   * @return Value for property 'chatId'.
   */
  public long getChatId() {
    return chatId;
  }

  /**
   * Setter for property 'selected'.
   *
   * @param selected Value to set for property 'selected'.
   */
  public void setSelected(boolean selected) {
    this.selected.set(selected);
  }

  /**
   * Getter for property 'selected'.
   *
   * @return Value for property 'selected'.
   */
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
