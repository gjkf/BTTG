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
package me.gjkf.bttg.controls.message;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import me.gjkf.bttg.BTTG;
import me.gjkf.bttg.util.ChatInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drinkless.tdlib.TdApi;

import java.util.Arrays;

/**
 * The text of the message. Constructs a {@link TextFlow} object with different styles,
 * corresponding to the {@link org.drinkless.tdlib.TdApi.TextEntity} objects in the message.
 *
 * @author Davide Cossu
 */
public class MessageText extends Pane implements IMessage {

  private static final Logger logger = LogManager.getLogger(MessageText.class);

  private final long chatId;
  private final long messageId;

  /**
   * The label of the message.
   */
  private final TextFlow textLabel;

  public MessageText(long chatId, long messageId) {
    super();
    this.chatId = chatId;
    this.messageId = messageId;
    TdApi.Message m = ChatInfo.getMessage(chatId, messageId);
    logger.debug("Message: {}", m);
    TdApi.FormattedText formattedText = ((TdApi.MessageText) m.content).text;
    textLabel = handleEntities(formattedText);
    initialize();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize() {
    setMinHeight(50);
    setPrefWidth(500);
    textLabel.getStyleClass().add("chatMessageText");
    textLabel.setPrefWidth(getPrefWidth());
    getChildren().add(textLabel);
  }

  /**
   * Creates the text that will be shown on screen, parsing the entities and adding them all
   * together.
   *
   * @param text The Telegram instance of the text in the message.
   * @return A {@link TextFlow} containing different {@link Text} objects with the correct
   *     formatting.
   */
  private TextFlow handleEntities(TdApi.FormattedText text) {
    // The final text component that will be showed
    TextFlow textFlow = new TextFlow();

    int offset = 0;
    for (TdApi.TextEntity entity : text.entities) {
      // Adding the text from the current offset up to the next entity
      Text toAdd = new Text(text.text.substring(offset, entity.offset));
      textFlow.getChildren().add(toAdd);

      switch (entity.type.getConstructor()) {
        case TdApi.TextEntityTypeBold.CONSTRUCTOR:
          String boldText = text.text.substring(entity.offset, entity.offset + entity.length);
          Text bold = new Text(boldText);
          bold.setStyle("-fx-font-weight: bold;");
          textFlow.getChildren().add(bold);
          break;
        case TdApi.TextEntityTypeBotCommand.CONSTRUCTOR:
          break;
        case TdApi.TextEntityTypeCashtag.CONSTRUCTOR:
          break;
        case TdApi.TextEntityTypeEmailAddress.CONSTRUCTOR:
          break;
        case TdApi.TextEntityTypeHashtag.CONSTRUCTOR:
          String hashtagText = text.text.substring(entity.offset, entity.offset + entity.length);
          Text hashtag = new Text(hashtagText);
          hashtag.getStyleClass().add("chatMessageTextHashtag");
          hashtag.setOnMouseClicked(
              event -> {
                // Retrieve and fill the map
                ChatInfo.getHashtags(hashtagText, chatId, 100);
                // TODO: 11/1/18 Add the "go to message" function and list in a pretty way the
                // messages
                Arrays.stream(BTTG.getMessages().get(chatId).messages)
                    .filter(message -> (message.content instanceof TdApi.MessageText))
                    .filter(t -> ((TdApi.MessageText) t.content).text.text.contains(hashtagText))
                    .forEach(tag -> logger.debug(tag.id));
              });
          textFlow.getChildren().add(hashtag);
          break;
        case TdApi.TextEntityTypeItalic.CONSTRUCTOR:
          String italicText = text.text.substring(entity.offset, entity.offset + entity.length);
          Text italic = new Text(italicText);
          italic.setStyle("-fx-font-style: italic;");
          textFlow.getChildren().add(italic);
          break;
        case TdApi.TextEntityTypeMention.CONSTRUCTOR:
          break;
        case TdApi.TextEntityTypeMentionName.CONSTRUCTOR:
          break;
        case TdApi.TextEntityTypePhoneNumber.CONSTRUCTOR:
          break;
        case TdApi.TextEntityTypeCode.CONSTRUCTOR:
        case TdApi.TextEntityTypePre.CONSTRUCTOR:
        case TdApi.TextEntityTypePreCode.CONSTRUCTOR:
          String codeText = text.text.substring(entity.offset, entity.offset + entity.length);
          Text code = new Text(codeText);
          code.getStyleClass().add("chatMessageTextCode");
          textFlow.getChildren().add(code);
          break;
        case TdApi.TextEntityTypeTextUrl.CONSTRUCTOR:
          break;
        case TdApi.TextEntityTypeUrl.CONSTRUCTOR:
          break;
        default:
          logger.warn("Unrecognized constructor: {}", entity);
      }
      // Updating the offset to add eventually new text
      offset = entity.offset + entity.length;
    }
    // Adding the last unformatted part, if it exists
    Text last = new Text(text.text.substring(offset));
    textFlow.getChildren().add(last);
    textFlow.setTextAlignment(TextAlignment.LEFT);
    return textFlow;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getChatId() {
    return chatId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getMessageId() {
    return messageId;
  }
}
