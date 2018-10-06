package me.gjkf.bttg.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import me.gjkf.bttg.BTTG;
import me.gjkf.bttg.controls.ChatControl;
import me.gjkf.bttg.controls.ChatItem;
import org.drinkless.tdlib.TdApi;
import org.drinkless.tdlib.example.Example;

public class BTTGMainScene extends StackPane {

  public BTTGMainScene() {
    super();
    setAlignment(Pos.CENTER);
    setPadding(new Insets(50));
    setScaleX(2);
    setScaleY(2);

    setPrefWidth(1000);
    setPrefHeight(1000);

    HBox hBox = new HBox();
    hBox.setAlignment(Pos.CENTER);
    hBox.setSpacing(10);

    ChatControl control = new ChatControl();
    control.getChildren().add(new ChatItem(12));
    control.getChildren().add(new ChatItem(13));
    control.getChildren().add(new ChatItem(14));
    control.getChildren().add(new ChatItem(15));
    control.getChildren().add(new ChatItem(16));
    control.getChildren().add(new ChatItem(17));
    control.getChildren().add(new ChatItem(18));
    control.getChildren().add(new ChatItem(19));
    control.getChildren().add(new ChatItem(12));
    control.getChildren().add(new ChatItem(13));
    control.getChildren().add(new ChatItem(14));
    control.getChildren().add(new ChatItem(15));
    control.getChildren().add(new ChatItem(16));
    control.getChildren().add(new ChatItem(17));
    control.getChildren().add(new ChatItem(18));
    control.getChildren().add(new ChatItem(19));
    control.getChildren().add(new ChatItem(12));
    control.getChildren().add(new ChatItem(13));
    control.getChildren().add(new ChatItem(14));
    control.getChildren().add(new ChatItem(15));
    control.getChildren().add(new ChatItem(16));
    control.getChildren().add(new ChatItem(17));
    control.getChildren().add(new ChatItem(18));
    control.getChildren().add(new ChatItem(19));

    ScrollPane scrollPane = new ScrollPane(control);
    scrollPane.setMaxHeight(150);
    scrollPane.setFitToHeight(true);

    VBox messageBox = new VBox();
    messageBox.setAlignment(Pos.CENTER);
    messageBox.setSpacing(10);

    TextField messageText = new TextField();
    messageBox.getChildren().add(messageText);

    Button sendMessage = new Button("Send message");
    sendMessage.setOnMousePressed(
        event -> {
          BTTG.getClient()
              .send(new TdApi.CreatePrivateChat(638240409, true), new Example.DefaultHandler());
          TdApi.InputMessageContent content =
              new TdApi.InputMessageText(
                  new TdApi.FormattedText(messageText.getText(), null), false, true);
          BTTG.getClient()
              .send(
                  new TdApi.SendMessage(638240409, 0, false, false, null, content),
                  new Example.DefaultHandler());
        });
    messageBox.getChildren().add(sendMessage);

    hBox.getChildren().addAll(scrollPane, messageBox);

    Pane p = new Pane(hBox);

    HBox h = new HBox();
    h.setPrefSize(1000, 1000);
    h.setAlignment(Pos.CENTER);

    VBox v = new VBox();
    v.setPrefSize(p.getPrefWidth(), h.getPrefHeight());
    v.setAlignment(Pos.CENTER);

    v.getChildren().add(p);
    h.getChildren().add(v);

    getChildren().add(h);
  }
}
