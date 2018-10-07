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
