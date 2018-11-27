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

import me.gjkf.bttg.BTTG;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drinkless.tdlib.TdApi;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Utility class to ease the retrieval of information.
 *
 * @author Davide Cossu
 */
public final class ChatInfo {

  private static final Logger logger = LogManager.getLogger(ChatInfo.class.getName());

  private ChatInfo() {}

  /**
   * Given the chat id and the message id, returns the {@link org.drinkless.tdlib.TdApi.Message
   * message} object corresponding to the data.
   *
   * @param chatId The chatId from which search the message.
   * @param messageId The messageId of the message.
   * @return The message object.
   */
  public static TdApi.Message getMessage(long chatId, long messageId) {
    CountDownLatch latch = new CountDownLatch(1);
    ExecutorService service = Executors.newCachedThreadPool();
    TdApi.Message[] ret = new TdApi.Message[1];
    service.submit(
        () -> {
          List<TdApi.Message> messages = retrieveMessages(chatId, 100);
          messages.forEach(
              message -> {
                if (message.id == messageId) {
                  ret[0] = message;
                  latch.countDown();
                }
              });
        });
    try {
      latch.await(100, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      logger.throwing(e);
    }
    return ret[0];
  }

  /**
   * Retrieves the last {@code limit} messages from the given chat.
   *
   * @param chatId The id of the chat from which gather the messages.
   * @param limit The maximum number of messages to get.
   * @return A list containing the messages.
   * @see TdApi.Message
   */
  public static List<TdApi.Message> retrieveMessages(long chatId, int limit) {
    // We need to call multiple times the GetChatHistory function since it will retrieve
    // different amount of messages up to 100 (or a set limit). To optimize it all, it will
    // just fetch some, not all of them.
    long from = 0;
    int total = limit;
    CountDownLatch latch = new CountDownLatch(total);
    List<TdApi.Message> ret = Collections.synchronizedList(new LinkedList<>());
    while (total > 0) {
      logger.trace("(B) From: {}, Total: {}", from, total);
      int finalTotal = total;
      BTTG.getClient()
          .send(
              new TdApi.GetChatHistory(chatId, from, 0, total, false),
              result -> {
                switch (result.getConstructor()) {
                  case TdApi.Messages.CONSTRUCTOR:
                    TdApi.Message[] messages = ((TdApi.Messages) result).messages;
                    ret.addAll(Arrays.asList(messages));
                    logger.info("{}={}/{}", chatId, ret.size(), finalTotal);
                    for (TdApi.Message message : messages) {

                      TdApi.MessageContent content = message.content;
                      // TODO: 10/20/18 Fill in the ifs with relevant controls and handles
                      if (content instanceof TdApi.MessageText) {

                      } else if (content instanceof TdApi.MessageAudio) {

                      } else if (content instanceof TdApi.MessageAnimation) {

                      } else if (content instanceof TdApi.MessageBasicGroupChatCreate) {

                      } else if (content instanceof TdApi.MessageCall) {

                      } else if (content instanceof TdApi.MessageChatAddMembers) {

                      } else if (content instanceof TdApi.MessageChatChangePhoto) {

                      } else if (content instanceof TdApi.MessageChatChangeTitle) {

                      } else if (content instanceof TdApi.MessageChatDeleteMember) {

                      } else if (content instanceof TdApi.MessageChatDeletePhoto) {

                      } else if (content instanceof TdApi.MessageChatJoinByLink) {

                      } else if (content instanceof TdApi.MessageChatSetTtl) {

                      } else if (content instanceof TdApi.MessageChatUpgradeFrom) {

                      } else if (content instanceof TdApi.MessageChatUpgradeTo) {

                      } else if (content instanceof TdApi.MessageContact) {

                      } else if (content instanceof TdApi.MessageContactRegistered) {

                      } else if (content instanceof TdApi.MessageCustomServiceAction) {

                      } else if (content instanceof TdApi.MessageDocument) {

                      } else if (content instanceof TdApi.MessageExpiredPhoto) {

                      } else if (content instanceof TdApi.MessageExpiredVideo) {

                      } else if (content instanceof TdApi.MessageGame) {

                      } else if (content instanceof TdApi.MessageGameScore) {

                      } else if (content instanceof TdApi.MessageInvoice) {

                      } else if (content instanceof TdApi.MessageLocation) {

                      } else if (content instanceof TdApi.MessagePaymentSuccessful) {

                      } else if (content instanceof TdApi.MessagePaymentSuccessfulBot) {

                      } else if (content instanceof TdApi.MessagePhoto) {

                      } else if (content instanceof TdApi.MessagePinMessage) {

                      } else if (content instanceof TdApi.MessageScreenshotTaken) {

                      } else if (content instanceof TdApi.MessageSticker) {

                      } else if (content instanceof TdApi.MessageSupergroupChatCreate) {

                      } else if (content instanceof TdApi.MessageVenue) {

                      } else if (content instanceof TdApi.MessageVideo) {

                      } else if (content instanceof TdApi.MessageVideoNote) {

                      } else if (content instanceof TdApi.MessageVoiceNote) {

                      } else if (content instanceof TdApi.MessageWebsiteConnected) {

                      } else if (content instanceof TdApi.MessageUnsupported) {

                      }
                    }
                    latch.countDown();
                    break;
                  default:
                    logger.warn("Unrecognized constructor:\n{}", result);
                    break;
                }
              });
      try {
        latch.await(150, TimeUnit.MILLISECONDS);
      } catch (InterruptedException e) {
        logger.throwing(e);
      }
      from = ret.get(ret.size() - 1).id;
      logger.debug("Tot: {}", total);
      total -= ret.size();
      logger.trace("(E) From: {}, Total: {}, Size: {}\n", from, total, ret.size());
    }
    return ret;
  }

  /**
   * Fills the {@link BTTG#getHashtags()} map with {@link org.drinkless.tdlib.TdApi.Hashtags
   * hashtags} from the chat given the identifier.
   *
   * @param hashtag The String of the identifier.
   * @param chatId  The chatId.
   * @param limit   The number of hashtags to retrieve.
   */
  public static void getHashtags(String hashtag, long chatId, int limit) {
    CountDownLatch latch = new CountDownLatch(1);
    List<TdApi.Message> ret = Collections.synchronizedList(new LinkedList<>());
    BTTG.getClient()
        .send(
            new TdApi.SearchHashtags(hashtag, limit),
            result -> {
              // TODO: 11/27/18 Test performance. In case it is too slow, see #getMessage
              switch (result.getConstructor()) {
                case TdApi.Hashtags.CONSTRUCTOR:
                  ret.addAll(
                      retrieveMessages(chatId, 100)
                          .stream()
                          .filter(message -> (message.content instanceof TdApi.MessageText))
                          .filter(t -> ((TdApi.MessageText) t.content).text.text.contains(hashtag))
                          .collect(Collectors.toCollection(LinkedList::new)));
                  logger.info(ret);
                  latch.countDown();
                  break;
                default:
                  logger.warn("Unrecognized constructor:\n{}", result);
              }
            });
    try {
      latch.await(100, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      logger.throwing(e);
    }
  }

  public static Object getInfo(long chatId) {
    Object[] ret = {null};
    BTTG.getClient()
        .send(
            new TdApi.GetChat(chatId),
            result -> {
              logger.debug("Chat: " + result);
              switch (((TdApi.Chat) result).type.getConstructor()) {
                case TdApi.ChatTypePrivate.CONSTRUCTOR:
                  ret[0] = getPrivateChatInfo(chatId);
                  break;
                case TdApi.ChatTypeSupergroup.CONSTRUCTOR:
                  long superGroupId =
                      (((TdApi.ChatTypeSupergroup) ((TdApi.Chat) result).type)).supergroupId;
                  ret[0] = getSuperGroupInfo(superGroupId);
                  break;
                case TdApi.ChatTypeBasicGroup.CONSTRUCTOR:
                  long basicGroupId =
                      (((TdApi.ChatTypeBasicGroup) ((TdApi.Chat) result).type)).basicGroupId;
                  ret[0] = getGroupInfo(basicGroupId);
                  break;
              }
            });
    return ret[0];
  }

  private static TDUser getPrivateChatInfo(long id) {
    TdApi.UserFullInfo[] info = {null};
    TdApi.User[] user = {null};
    BTTG.getClient()
        .send(
            new TdApi.GetUserFullInfo((int) id),
            o -> {
              System.out.println(o);
              if (o.getConstructor() == TdApi.UserFullInfo.CONSTRUCTOR) {
                info[0] = (TdApi.UserFullInfo) o;
              } else {
                logger.debug(id + " " + o);
              }
            });
    BTTG.getClient()
        .send(
            new TdApi.GetUser((int) id),
            o -> {
              if (o.getConstructor() == TdApi.User.CONSTRUCTOR) {
                user[0] = (TdApi.User) o;
              } else {
                logger.debug(id + " " + o);
              }
            });
    return new TDUser(
        info[0].isBlocked,
        info[0].canBeCalled,
        info[0].hasPrivateCalls,
        info[0].bio,
        info[0].shareText,
        info[0].groupInCommonCount,
        info[0].botInfo,
        user[0].id,
        user[0].firstName,
        user[0].lastName,
        user[0].username,
        user[0].phoneNumber,
        user[0].status,
        user[0].profilePhoto,
        user[0].outgoingLink,
        user[0].incomingLink,
        user[0].isVerified,
        user[0].restrictionReason,
        user[0].haveAccess,
        user[0].type,
        user[0].languageCode);
  }

  private static TDSuperGroup getSuperGroupInfo(long superGroupId) {
    TdApi.SupergroupFullInfo[] info = {null};
    TdApi.Supergroup[] superGroup = {null};
    BTTG.getClient()
        .send(
            new TdApi.GetSupergroupFullInfo((int) superGroupId),
            o -> {
              if (o.getConstructor() == TdApi.SupergroupFullInfo.CONSTRUCTOR) {
                info[0] = (TdApi.SupergroupFullInfo) o;
              } else {
                logger.debug(superGroupId + " " + o);
              }
            });
    BTTG.getClient()
        .send(
            new TdApi.GetSupergroup((int) superGroupId),
            o -> {
              if (o.getConstructor() == TdApi.Supergroup.CONSTRUCTOR) {
                superGroup[0] = (TdApi.Supergroup) o;
              } else {
                logger.debug(superGroupId + " " + o);
              }
            });
    return new TDSuperGroup(
        info[0].description,
        info[0].memberCount,
        info[0].administratorCount,
        info[0].restrictedCount,
        info[0].bannedCount,
        info[0].canGetMembers,
        info[0].canSetUsername,
        info[0].canSetStickerSet,
        info[0].isAllHistoryAvailable,
        info[0].stickerSetId,
        info[0].inviteLink,
        info[0].pinnedMessageId,
        info[0].upgradedFromBasicGroupId,
        info[0].upgradedFromMaxMessageId,
        superGroup[0].id,
        superGroup[0].username,
        superGroup[0].date,
        superGroup[0].status,
        superGroup[0].anyoneCanInvite,
        superGroup[0].signMessages,
        superGroup[0].isChannel,
        superGroup[0].isVerified,
        superGroup[0].restrictionReason);
  }

  private static TDGroup getGroupInfo(long basicGroupId) {
    TdApi.BasicGroupFullInfo[] info = {null};
    TdApi.BasicGroup[] group = {null};
    BTTG.getClient()
        .send(
            new TdApi.GetBasicGroupFullInfo((int) basicGroupId),
            o -> {
              if (o.getConstructor() == TdApi.BasicGroupFullInfo.CONSTRUCTOR) {
                info[0] = (TdApi.BasicGroupFullInfo) o;
              } else {
                logger.debug(basicGroupId + " " + o);
              }
            });
    BTTG.getClient()
        .send(
            new TdApi.GetBasicGroup((int) basicGroupId),
            o -> {
              if (o.getConstructor() == TdApi.BasicGroup.CONSTRUCTOR) {
                group[0] = (TdApi.BasicGroup) o;
              } else {
                logger.debug(basicGroupId + " " + o);
              }
            });
    return new TDGroup(
        info[0].creatorUserId,
        info[0].members,
        info[0].inviteLink,
        group[0].id,
        group[0].memberCount,
        group[0].status,
        group[0].everyoneIsAdministrator,
        group[0].isActive,
        group[0].upgradedToSupergroupId);
  }
}
