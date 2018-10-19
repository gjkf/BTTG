package me.gjkf.bttg.util;

import me.gjkf.bttg.BTTG;
import org.drinkless.tdlib.TdApi;

/**
 * Retrieves the info of one chat given the id
 */
public final class ChatInfo {

  private ChatInfo() {}

  public static void getInfo(long chatId) {
    BTTG.getClient()
        .send(
            new TdApi.GetChat(chatId),
            result -> {
              System.out.println(result);
              switch (((TdApi.Chat) result).type.getConstructor()) {
                case TdApi.ChatTypePrivate.CONSTRUCTOR:
                  System.out.println("Info (P) : " + getPrivateChatInfo(chatId));
                  break;
                case TdApi.ChatTypeSupergroup.CONSTRUCTOR:
                  long superGroupId =
                      (((TdApi.ChatTypeSupergroup) ((TdApi.Chat) result).type)).supergroupId;
                  System.out.println("Info (S) : " + getSuperGroupInfo(superGroupId));
                  break;
                case TdApi.ChatTypeBasicGroup.CONSTRUCTOR:
                  long basicGroupId =
                      (((TdApi.ChatTypeBasicGroup) ((TdApi.Chat) result).type)).basicGroupId;
                  System.out.println("Info (G) : " + getGroupInfo(basicGroupId));
                  break;
                default:
                  System.out.println("Res: " + result);
              }
            });
  }

  private static TdApi.UserFullInfo getPrivateChatInfo(long id) {
    TdApi.UserFullInfo[] info = {null};
    BTTG.getClient()
        .send(
            new TdApi.GetUserFullInfo((int) id),
            o -> {
              if (o.getConstructor() == TdApi.UserFullInfo.CONSTRUCTOR) {
                info[0] = (TdApi.UserFullInfo) o;
                System.out.println("Test (P) : " + o + " INFO: " + info[0]);
              } else {
                System.out.println(id + " " + o);
              }
            });
    System.out.println("INFO (OUT): " + info[0]);
    return info[0];
  }

  private static TdApi.SupergroupFullInfo getSuperGroupInfo(long superGroupId) {
    TdApi.SupergroupFullInfo[] info = {null};
    BTTG.getClient()
        .send(
            new TdApi.GetSupergroupFullInfo((int) superGroupId),
            o -> {
              if (o.getConstructor() == TdApi.SupergroupFullInfo.CONSTRUCTOR) {
                info[0] = (TdApi.SupergroupFullInfo) o;
//                System.out.println("Test (S) : " + o + " INFO: " + info[0]);
              } else {
//                System.out.println(superGroupId + " " + o);
              }
            });
    return info[0];
  }

  private static TdApi.BasicGroupFullInfo getGroupInfo(long basicGroupId) {
    TdApi.BasicGroupFullInfo[] info = {null};
    BTTG.getClient()
        .send(
            new TdApi.GetBasicGroupFullInfo((int) basicGroupId),
            o -> {
              if (o.getConstructor() == TdApi.BasicGroupFullInfo.CONSTRUCTOR) {
                info[0] = (TdApi.BasicGroupFullInfo) o;
//                System.out.println("Test (G) : " + o + " INFO: " + info[0]);
              } else {
//                System.out.println(basicGroupId + " " + o);
              }
            });
    return info[0];
  }
}
