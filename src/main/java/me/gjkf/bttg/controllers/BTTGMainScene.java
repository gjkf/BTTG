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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

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
    // TODO: Fix the loading timings. It appears it takes too much time to retrieve the information
//        BTTG.getClient()
//            .send(
//                new TdApi.GetChats(Long.MAX_VALUE, 0, 5),
//                object -> {
//                  if (object.getConstructor() == TdApi.Chats.CONSTRUCTOR) {
//                    long[] chatIds = ((TdApi.Chats) object).chatIds;
//                    List<Long> ids =
//                        Arrays.stream(chatIds)
//                            .boxed()
//                            .collect(Collectors.toCollection(LinkedList::new));
//                    // Populate the control with ChatItems corresponding to the chats
//
//                    List<Thread> requests = new ArrayList<>();
//                    for (long id : ids) {
//                      requests.add(new Thread(() -> ChatInfo.getInfo(id)));
//                    }
//                    for (int i = 0; i < requests.size() - 1; i++) {
//                      requests.get(i).start();
//                      try {
//                        requests.get(i + 1).join();
//                      } catch (InterruptedException e) {
//                        e.printStackTrace();
//                      }
//                    }
//                  } else {
//                    System.out.println("Obj: " + object);
//                  }
//                });
    BTTG.getChatList(10);
    System.out.println("BTTG: " + BTTG.getChatList());

    ScrollPane scrollPane = new ScrollPane(control);
    scrollPane.setMaxHeight(150);
    scrollPane.setPrefWidth(150);
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
              .send(
                  new TdApi.CreatePrivateChat(
                      (int) control.getSelected().findFirst().get().getUserId(), true),
                  new Example.DefaultHandler());
          TdApi.InputMessageContent content =
              new TdApi.InputMessageText(
                  new TdApi.FormattedText(messageText.getText(), null), false, true);
          BTTG.getClient()
              .send(
                  new TdApi.SendMessage(
                      (int) control.getSelected().findFirst().get().getUserId(),
                      0,
                      false,
                      false,
                      null,
                      content),
                  new Example.DefaultHandler());
        });
//    messageBox.getChildren().add(sendMessage);

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

  private void addPrivateChat(
      AtomicReference<CountDownLatch> latch, AtomicReference<ChatControl> control, long id) {
    BTTG.getClient()
        .send(
            new TdApi.GetUser((int) id),
            o -> {
              if (o.getConstructor() == TdApi.User.CONSTRUCTOR) {
                TdApi.User info = (TdApi.User) o;

                control.get().getChildren().add(new ChatItem(info));
                latch.get().countDown();
              } else {
                System.out.println(id + " " + o);
              }
            });
  }

  private void addSuperGroup(
      AtomicReference<CountDownLatch> latch,
      AtomicReference<ChatControl> control,
      long superGroupId) {
    BTTG.getClient()
        .send(
            new TdApi.GetSupergroupFullInfo((int) superGroupId),
            o -> {
              if (o.getConstructor() == TdApi.SupergroupFullInfo.CONSTRUCTOR) {

                TdApi.SupergroupFullInfo info = (TdApi.SupergroupFullInfo) o;

                control.get().getChildren().add(new ChatItem(info));
                latch.get().countDown();
              } else {
                System.out.println(superGroupId + " " + o);
              }
            });
  }

  private void addGroup(
      AtomicReference<CountDownLatch> latch,
      AtomicReference<ChatControl> control,
      long basicGroupId) {
    BTTG.getClient()
        .send(
            new TdApi.GetBasicGroup((int) basicGroupId),
            o -> {
              if (o.getConstructor() == TdApi.BasicGroup.CONSTRUCTOR) {
                TdApi.BasicGroup info = (TdApi.BasicGroup) o;

                control.get().getChildren().add(new ChatItem(info));
                latch.get().countDown();
              } else {
                System.out.println(basicGroupId + " " + o);
              }
            });
  }
}
