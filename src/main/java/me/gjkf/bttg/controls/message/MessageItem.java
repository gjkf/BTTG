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
package me.gjkf.bttg.controls.message;

import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The clickable object that represents a chat message.
 *
 * @author Davide Cossu
 */
public class MessageItem extends VBox {

  private static final Logger logger = LogManager.getLogger(MessageItem.class);

  private final IMessage message;

  public MessageItem(IMessage message) {
    this.message = message;
    initialize();
  }

  private void initialize() {
    getStyleClass().add("chatMessageItem");
    setPadding(new Insets(10, 0, 10, 0));

    MessageLabel label = new MessageLabel(message.getChatId(), message.getMessageId());
    setMargin(label, new Insets(10, 0, 0, 0));
    getChildren().add(label);
    // TODO: 11/27/18 Add the support for different types of messages
    getChildren().add(new MessageText(message.getChatId(), message.getMessageId()));

    setOnMouseClicked(
        event -> {
          if (event.getButton() == MouseButton.SECONDARY) logger.info(message.getMessageId());
        });
  }
}
