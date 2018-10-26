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

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The clickable object that represents a chat message.
 */
public class MessageItem extends VBox {

  private static final Logger logger = LogManager.getLogger(MessageItem.class);

  public MessageItem(long chatId, long messageId) {
    getStyleClass().add("chatMessageItem");
    setPadding(new Insets(10, 0, 10, 0));
    setSpacing(10);
    getChildren().add(new MessageLabel(chatId));
    getChildren().add(new MessageText(chatId, messageId));
//    setVgrow(this, Priority.ALWAYS);
  }
}
