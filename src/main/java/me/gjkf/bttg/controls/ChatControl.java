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

import javafx.scene.layout.VBox;

import java.util.stream.Stream;

/**
 * The {@link VBox} containing all the different {@link ChatItem} instances.
 */
public class ChatControl extends VBox {

  public ChatControl() {
    getStyleClass().add("chatControl");
  }

  /**
   * Returns the selected {@link ChatItem} elements in the list.
   *
   * @return A stream containing the selected {@link ChatItem}s
   */
  public Stream<ChatItem> getSelected() {
    return getChildren()
        .stream()
        .filter(child -> child instanceof ChatItem)
        .map(ChatItem.class::cast)
        .filter(ChatItem::getSelected)
        .distinct();
  }
}
