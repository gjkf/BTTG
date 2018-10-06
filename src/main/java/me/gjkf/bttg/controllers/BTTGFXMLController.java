package me.gjkf.bttg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import me.gjkf.bttg.BTTG;
import org.drinkless.tdlib.TdApi;
import org.drinkless.tdlib.example.Example;

/**
 * Controller for the main application
 *
 * @see me.gjkf.bttg.BTTG
 */
public class BTTGFXMLController {
  @FXML
  private TextField message;

  @FXML
  protected void sendMessage(ActionEvent event) {
    BTTG.getClient().send(new TdApi.CreatePrivateChat(638240409, true),
        new Example.DefaultHandler());
//    BTTG.getClient().send(new TdApi.SearchPublicChat("Testy113"), new Example.DefaultHandler());
    TdApi.InputMessageContent content =
        new TdApi.InputMessageText(new TdApi.FormattedText(message.getText(), null), false, true);
    BTTG.getClient()
        .send(
            new TdApi.SendMessage(638240409, 0, false, false, null, content),
            new Example.DefaultHandler());
  }

  @FXML
  public void getChats(ActionEvent event) {
    BTTG.getClient().send(new TdApi.GetChats(Long.MAX_VALUE, 0, 15), new Example.DefaultHandler());
  }

}
