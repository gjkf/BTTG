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
