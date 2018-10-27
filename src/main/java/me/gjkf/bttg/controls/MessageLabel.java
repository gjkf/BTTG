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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import me.gjkf.bttg.BTTG;
import me.gjkf.bttg.util.ChatInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * An object representing the sender label. Allows for customizable coloring through CSS or
 * automatic coloring based on the chatId.
 *
 * @author Davide Cossu
 */
public class MessageLabel extends Pane {

  private static final Logger logger = LogManager.getLogger(MessageLabel.class);

  private final long chatId;
  private final long messageId;
  private Text text;

  public MessageLabel(long chatId, long messageId) {
    super();
    this.chatId = chatId;
    this.messageId = messageId;
    if (BTTG.getChats().containsKey(chatId)) {
      String label = "";
      if (BTTG.getChats().get((long) ChatInfo.getMessage(chatId, messageId).senderUserId) != null) {
        label =
            BTTG.getChats().get((long) ChatInfo.getMessage(chatId, messageId).senderUserId).title;
      } else { // It's a channel so directly get the chatId
        label = BTTG.getChats().get(chatId).title;
      }
      text = new Text(label);
      text.getStyleClass().add("chatMessageSender");
      getChildren().add(text);
    }
    initialize();
  }

  private void initialize() {
    getStyleClass().add("chatMessageSender");
    setPrefWidth(60);
    setMaxWidth(100);
    setPadding(new Insets(0, 0, 5, 0));

    Color color = Color.DARKCYAN;
    switch (ChatInfo.getMessage(chatId, messageId).senderUserId % 5) {
      case 0:
        color = Color.AQUA;
        break;
      case 1:
        color = Color.RED;
        break;
      case 2:
        color = Color.WHITE;
        break;
      case 3:
        color = Color.BLACK;
        break;
      case 4:
        color = Color.ORANGE;
        break;
    }
    text.setStyle(
        "-fx-fill: "
            + String.format(
            "#%02x%02x%02x",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255))
            + ";");
  }
}
