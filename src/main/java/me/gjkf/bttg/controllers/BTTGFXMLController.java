package me.gjkf.bttg.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import me.gjkf.bttg.BTTG;
import org.drinkless.tdlib.TdApi;
import org.drinkless.tdlib.example.Example;

/**
 * Controller for the main application.
 *
 * @see me.gjkf.bttg.BTTG
 */
public class BTTGFXMLController {
  @FXML
  private TextField message;

  @FXML
  protected void sendMessage(ActionEvent event) {
    System.out.println(message.getText());
    TdApi.InlineKeyboardButton[] row = {
        new TdApi.InlineKeyboardButton(
            "https://telegram.org?1", new TdApi.InlineKeyboardButtonTypeUrl()),
        new TdApi.InlineKeyboardButton(
            "https://telegram.org?2", new TdApi.InlineKeyboardButtonTypeUrl()),
        new TdApi.InlineKeyboardButton(
            "https://telegram.org?3", new TdApi.InlineKeyboardButtonTypeUrl())
    };
    TdApi.ReplyMarkup replyMarkup =
        new TdApi.ReplyMarkupInlineKeyboard(new TdApi.InlineKeyboardButton[][]{row, row, row});
    TdApi.InputMessageContent content =
        new TdApi.InputMessageText(new TdApi.FormattedText(message.getText(), null), false, true);
    BTTG.getClient()
        .send(
            new TdApi.SendMessage(638240409, 0, false, false, replyMarkup, content),
            new Example.DefaultHandler());
  }
}
