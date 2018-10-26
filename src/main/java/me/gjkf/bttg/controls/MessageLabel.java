/*
 * bttg
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
import javafx.scene.text.Text;
import me.gjkf.bttg.BTTG;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * An object representing the sender label. Allows for customizable coloring through CSS or
 * automatic coloring based on the chatId.
 */
public class MessageLabel extends Pane {

  private static final Logger logger = LogManager.getLogger(MessageLabel.class);

  private final long chatId;

  public MessageLabel(long chatId) {
    super();
    this.chatId = chatId;
    if (BTTG.getChats().containsKey(chatId)) {
      Text text = new Text(String.valueOf(BTTG.getChats().get(chatId).title));
      text.getStyleClass().add("chatMessageSender");
      getChildren().add(text);
    }
    initialize();

  }

  private void initialize() {
//    getStyleClass().add("chatMessageSender");
    setPrefHeight(20);
    setPrefWidth(60);
    setMaxWidth(100);
    setPadding(new Insets(10, 0, 10, 0));

    //      Color color = Color.DARKCYAN;
    //      switch ((int) (chatId % 5)) {
    //        case 0:
    //          color = Color.AQUA;
    //          break;
    //        case 1:
    //          color = Color.RED;
    //          break;
    //        case 2:
    //          color = Color.WHITE;
    //          break;
    //        case 3:
    //          color = Color.GREEN;
    //          break;
    //        case 4:
    //          color = Color.ORANGE;
    //          break;
    //      }
    //    setStyle(
    //        "-fx-text-fill: "
    //            + String.format(
    //                "#%02x%02x%02x",
    //                (int) (color.getRed() * 255),
    //                (int) (color.getGreen() * 255),
    //                (int) (color.getBlue() * 255)));
  }
}
