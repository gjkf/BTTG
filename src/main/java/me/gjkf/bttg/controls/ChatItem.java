package me.gjkf.bttg.controls;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Label;

/**
 * Represents the chat in the {@link ChatControl}.
 */
public class ChatItem extends Label {
  @FXML
  private long userId;

  public ChatItem() {
    initialize();
  }

  public ChatItem(long userId) {
    this.userId = userId;
    initialize();
  }

  private void initialize() {
    getStyleClass().add("chat-item");
    setOnMouseClicked(event -> System.out.println(this.getUserId()));
    setAccessibleRole(AccessibleRole.LIST_ITEM);
    setAlignment(Pos.CENTER);
    setMinSize(80, 20);
    setText(Long.toString(userId));
  }

  public long getUserId() {
    return userId;
  }
}
