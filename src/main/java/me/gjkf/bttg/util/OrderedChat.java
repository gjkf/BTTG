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

package me.gjkf.bttg.util;

/**
 * Ordered chat from the most recent to oldest one. Provides methods to compare two chats.
 */
public class OrderedChat implements Comparable<OrderedChat> {
  public final long order;
  public final long chatId;

  public OrderedChat(long order, long chatId) {
    this.order = order;
    this.chatId = chatId;
  }

  @Override
  public int compareTo(OrderedChat o) {
    if (order != o.order) {
      return o.order < order ? -1 : 1;
    }
    if (chatId != o.chatId) {
      return o.chatId < chatId ? -1 : 1;
    }
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof OrderedChat) {
      OrderedChat o = (OrderedChat) obj;
      return order == o.order && chatId == o.chatId;
    } else {
      throw new ClassCastException("Cannot cast " + obj.getClass() + "to OrderedChat");
    }
  }
}
