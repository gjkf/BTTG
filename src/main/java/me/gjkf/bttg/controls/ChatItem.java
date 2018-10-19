package me.gjkf.bttg.controls;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Label;
import me.gjkf.bttg.util.ChatInfo;

import java.util.Arrays;

/**
 * Represents the chat in the {@link ChatControl}.
 */
public class ChatItem extends Label {

  private final long chatId;

  public ChatItem(long chatId) {
    this.chatId = chatId;
    initialize();
  }

  private void initialize() {
    getStyleClass().add("chatItem");
    setAccessibleRole(AccessibleRole.LIST_ITEM);
    setAlignment(Pos.CENTER);
    setMinSize(100, 20);
    System.out.println("T: " + chatId);
    setText(String.valueOf(chatId));
    ChatInfo.getInfo(chatId);

    setOnMouseClicked(
        event -> {
          setSelected(!getSelected());
          System.out.println(Arrays.toString(((ChatControl) getParent()).getSelected().toArray()));
        });
  }

  public long getChatId() {
    return chatId;
  }

  public void setSelected(boolean selected) {
    this.selected.set(selected);
  }

  public boolean getSelected() {
    return selected.get();
  }

  private final BooleanProperty selected =
      new BooleanPropertyBase(false) {
        @Override
        protected void invalidated() {
          pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, get());
        }

        @Override
        public Object getBean() {
          return ChatItem.this;
        }

        @Override
        public String getName() {
          return "selected";
        }
      };

  private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");
}
