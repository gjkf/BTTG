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

/**
 * Retrieves the info of one chat given the id
 */
public final class ChatInfo {

  private static final Logger logger = LogManager.getLogger(ChatInfo.class.getName());

  private ChatInfo() {}

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
