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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Represents the chat in the {@link ChatControl}.
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
          logger.debug(Arrays.toString(((ChatControl) getParent()).getSelected().toArray()));
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
