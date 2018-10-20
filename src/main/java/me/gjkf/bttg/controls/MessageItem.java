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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import me.gjkf.bttg.BTTG;

/**
 * The clickable object that represents a chat message.
 */
public class MessageItem extends VBox {

  /**
   * The name of the sender.
   */
  private Label name;
  /**
   * The text of the message.
   */
  private Label text;

  public MessageItem(long chatId, long messageId) {
    super();
    initialize();
    if (BTTG.getChats().containsKey(chatId)) {
      name.setText(String.valueOf(BTTG.getChats().get(chatId).title));
    }
  }

  public MessageItem(String senderName, String messageText) {
    super();
    initialize();
    name.setText(senderName);
    text.setText(messageText);
  }

  private void initialize() {
    getStyleClass().add("messageItem");
//    setAlignment(Pos.CENTER_LEFT);
    setPrefWidth(150);
    setMaxWidth(300);
    name = new Label();
    text = new Label();
    text.setPadding(new Insets(10, 0, 0, 0));
    name.getStyleClass().add("chatMessageName");
    text.getStyleClass().add("chatMessageText");
    getChildren().addAll(name, text);
  }

}
