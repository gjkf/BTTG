package me.gjkf.bttg.handlers;

import me.gjkf.bttg.BTTG;
import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;

/**
 * Handler for updates. Provides an automatic way to fill {@link BTTG#chatList} and the other
 * fields.
 */

public class UpdatesHandler implements Client.ResultHandler {
  @Override
  public void onResult(TdApi.Object object) {
    switch (object.getConstructor()) {
      case TdApi.UpdateAuthorizationState.CONSTRUCTOR:
        //          onAuthorizationStateUpdated(((TdApi.UpdateAuthorizationState)
        // object).authorizationState);
        break;
      //
      case TdApi.UpdateUser.CONSTRUCTOR:
        TdApi.UpdateUser updateUser = (TdApi.UpdateUser) object;
        BTTG.getUsers().put(updateUser.user.id, updateUser.user);
        break;
      case TdApi.UpdateUserStatus.CONSTRUCTOR: {
        TdApi.UpdateUserStatus updateUserStatus = (TdApi.UpdateUserStatus) object;
        TdApi.User user = BTTG.getUsers().get(updateUserStatus.userId);
        synchronized (user) {
          user.status = updateUserStatus.status;
        }
        break;
      }
      case TdApi.UpdateBasicGroup.CONSTRUCTOR:
        TdApi.UpdateBasicGroup updateBasicGroup = (TdApi.UpdateBasicGroup) object;
        BTTG.getBasicGroups().put(updateBasicGroup.basicGroup.id, updateBasicGroup.basicGroup);
        break;
      case TdApi.UpdateSupergroup.CONSTRUCTOR:
        TdApi.UpdateSupergroup updateSupergroup = (TdApi.UpdateSupergroup) object;
        BTTG.getSupergroups().put(updateSupergroup.supergroup.id, updateSupergroup.supergroup);
        break;
      case TdApi.UpdateSecretChat.CONSTRUCTOR:
        TdApi.UpdateSecretChat updateSecretChat = (TdApi.UpdateSecretChat) object;
        BTTG.getSecretChats().put(updateSecretChat.secretChat.id, updateSecretChat.secretChat);
        break;

      case TdApi.UpdateNewChat.CONSTRUCTOR: {
        TdApi.UpdateNewChat updateNewChat = (TdApi.UpdateNewChat) object;
        TdApi.Chat chat = updateNewChat.chat;
        synchronized (chat) {
          BTTG.getChats().put(chat.id, chat);
          long order = chat.order;
          chat.order = 0;
          BTTG.setChatOrder(chat, order);
        }
        break;
      }
      case TdApi.UpdateChatTitle.CONSTRUCTOR: {
        TdApi.UpdateChatTitle updateChat = (TdApi.UpdateChatTitle) object;
        TdApi.Chat chat = BTTG.getChats().get(updateChat.chatId);
        synchronized (chat) {
          chat.title = updateChat.title;
        }
        break;
      }
      case TdApi.UpdateChatPhoto.CONSTRUCTOR: {
        TdApi.UpdateChatPhoto updateChat = (TdApi.UpdateChatPhoto) object;
        TdApi.Chat chat = BTTG.getChats().get(updateChat.chatId);
        synchronized (chat) {
          chat.photo = updateChat.photo;
        }
        break;
      }
      case TdApi.UpdateChatLastMessage.CONSTRUCTOR: {
        TdApi.UpdateChatLastMessage updateChat = (TdApi.UpdateChatLastMessage) object;
        TdApi.Chat chat = BTTG.getChats().get(updateChat.chatId);
        synchronized (chat) {
          chat.lastMessage = updateChat.lastMessage;
          BTTG.setChatOrder(chat, updateChat.order);
        }
        break;
      }
      case TdApi.UpdateChatOrder.CONSTRUCTOR: {
        TdApi.UpdateChatOrder updateChat = (TdApi.UpdateChatOrder) object;
        TdApi.Chat chat = BTTG.getChats().get(updateChat.chatId);
        synchronized (chat) {
          BTTG.setChatOrder(chat, updateChat.order);
        }
        break;
      }
      case TdApi.UpdateChatIsPinned.CONSTRUCTOR: {
        TdApi.UpdateChatIsPinned updateChat = (TdApi.UpdateChatIsPinned) object;
        TdApi.Chat chat = BTTG.getChats().get(updateChat.chatId);
        synchronized (chat) {
          chat.isPinned = updateChat.isPinned;
          BTTG.setChatOrder(chat, updateChat.order);
        }
        break;
      }
      case TdApi.UpdateChatReadInbox.CONSTRUCTOR: {
        TdApi.UpdateChatReadInbox updateChat = (TdApi.UpdateChatReadInbox) object;
        TdApi.Chat chat = BTTG.getChats().get(updateChat.chatId);
        synchronized (chat) {
          chat.lastReadInboxMessageId = updateChat.lastReadInboxMessageId;
          chat.unreadCount = updateChat.unreadCount;
        }
        break;
      }
      case TdApi.UpdateChatReadOutbox.CONSTRUCTOR: {
        TdApi.UpdateChatReadOutbox updateChat = (TdApi.UpdateChatReadOutbox) object;
        TdApi.Chat chat = BTTG.getChats().get(updateChat.chatId);
        synchronized (chat) {
          chat.lastReadOutboxMessageId = updateChat.lastReadOutboxMessageId;
        }
        break;
      }
      case TdApi.UpdateChatUnreadMentionCount.CONSTRUCTOR: {
        TdApi.UpdateChatUnreadMentionCount updateChat =
            (TdApi.UpdateChatUnreadMentionCount) object;
        TdApi.Chat chat = BTTG.getChats().get(updateChat.chatId);
        synchronized (chat) {
          chat.unreadMentionCount = updateChat.unreadMentionCount;
        }
        break;
      }
      case TdApi.UpdateMessageMentionRead.CONSTRUCTOR: {
        TdApi.UpdateMessageMentionRead updateChat = (TdApi.UpdateMessageMentionRead) object;
        TdApi.Chat chat = BTTG.getChats().get(updateChat.chatId);
        synchronized (chat) {
          chat.unreadMentionCount = updateChat.unreadMentionCount;
        }
        break;
      }
      case TdApi.UpdateChatReplyMarkup.CONSTRUCTOR: {
        TdApi.UpdateChatReplyMarkup updateChat = (TdApi.UpdateChatReplyMarkup) object;
        TdApi.Chat chat = BTTG.getChats().get(updateChat.chatId);
        synchronized (chat) {
          chat.replyMarkupMessageId = updateChat.replyMarkupMessageId;
        }
        break;
      }
      case TdApi.UpdateChatDraftMessage.CONSTRUCTOR: {
        TdApi.UpdateChatDraftMessage updateChat = (TdApi.UpdateChatDraftMessage) object;
        TdApi.Chat chat = BTTG.getChats().get(updateChat.chatId);
        synchronized (chat) {
          chat.draftMessage = updateChat.draftMessage;
          BTTG.setChatOrder(chat, updateChat.order);
        }
        break;
      }
      case TdApi.UpdateNotificationSettings.CONSTRUCTOR: {
        TdApi.UpdateNotificationSettings update = (TdApi.UpdateNotificationSettings) object;
        if (update.scope instanceof TdApi.NotificationSettingsScopeChat) {
          TdApi.Chat chat =
              BTTG.getChats().get(((TdApi.NotificationSettingsScopeChat) update.scope).chatId);
          synchronized (chat) {
            chat.notificationSettings = update.notificationSettings;
          }
        }
        break;
      }

      case TdApi.UpdateUserFullInfo.CONSTRUCTOR:
        TdApi.UpdateUserFullInfo updateUserFullInfo = (TdApi.UpdateUserFullInfo) object;
        BTTG.getUsersFullInfo().put(updateUserFullInfo.userId, updateUserFullInfo.userFullInfo);
        break;
      case TdApi.UpdateBasicGroupFullInfo.CONSTRUCTOR:
        TdApi.UpdateBasicGroupFullInfo updateBasicGroupFullInfo =
            (TdApi.UpdateBasicGroupFullInfo) object;
        BTTG.getBasicGroupsFullInfo()
            .put(
                updateBasicGroupFullInfo.basicGroupId, updateBasicGroupFullInfo.basicGroupFullInfo);
        break;
      case TdApi.UpdateSupergroupFullInfo.CONSTRUCTOR:
        TdApi.UpdateSupergroupFullInfo updateSupergroupFullInfo =
            (TdApi.UpdateSupergroupFullInfo) object;
        BTTG.getSupergroupsFullInfo()
            .put(
                updateSupergroupFullInfo.supergroupId, updateSupergroupFullInfo.supergroupFullInfo);
        break;
      default:
        break;
    }
  }
}
