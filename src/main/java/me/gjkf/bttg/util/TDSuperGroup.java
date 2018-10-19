package me.gjkf.bttg.util;

import org.drinkless.tdlib.TdApi;

public class TDSuperGroup {

  private final String description;
  private final int memberCount;
  private final int administratorCount;
  private final int restrictedCount;
  private final int bannedCount;
  private final boolean canGetMembers;
  private final boolean canSetUsername;
  private final boolean canSetStickerSet;
  private final boolean isAllHistoryAvailable;
  private final long stickerSetId;
  private final String inviteLink;
  private final long pinnedMessageId;
  private final int upgradedFromBasicGroupId;
  private final long upgradedFromMaxMessageId;
  private final int id;
  private final String username;
  private final int date;
  private final TdApi.ChatMemberStatus status;
  private final boolean anyoneCanInvite;
  private final boolean signMessages;
  private final boolean isChannel;
  private final boolean isVerified;
  private final String restrictionReason;

  public TDSuperGroup(
      String description,
      int memberCount,
      int administratorCount,
      int restrictedCount,
      int bannedCount,
      boolean canGetMembers,
      boolean canSetUsername,
      boolean canSetStickerSet,
      boolean isAllHistoryAvailable,
      long stickerSetId,
      String inviteLink,
      long pinnedMessageId,
      int upgradedFromBasicGroupId,
      long upgradedFromMaxMessageId,
      int id,
      String username,
      int date,
      TdApi.ChatMemberStatus status,
      boolean anyoneCanInvite,
      boolean signMessages,
      boolean isChannel,
      boolean isVerified,
      String restrictionReason) {
    this.description = description;
    this.memberCount = memberCount;
    this.administratorCount = administratorCount;
    this.restrictedCount = restrictedCount;
    this.bannedCount = bannedCount;
    this.canGetMembers = canGetMembers;
    this.canSetUsername = canSetUsername;
    this.canSetStickerSet = canSetStickerSet;
    this.isAllHistoryAvailable = isAllHistoryAvailable;
    this.stickerSetId = stickerSetId;
    this.inviteLink = inviteLink;
    this.pinnedMessageId = pinnedMessageId;
    this.upgradedFromBasicGroupId = upgradedFromBasicGroupId;
    this.upgradedFromMaxMessageId = upgradedFromMaxMessageId;
    this.id = id;
    this.username = username;
    this.date = date;
    this.status = status;
    this.anyoneCanInvite = anyoneCanInvite;
    this.signMessages = signMessages;
    this.isChannel = isChannel;
    this.isVerified = isVerified;
    this.restrictionReason = restrictionReason;
  }

  public String getDescription() {
    return description;
  }

  public int getMemberCount() {
    return memberCount;
  }

  public int getAdministratorCount() {
    return administratorCount;
  }

  public int getRestrictedCount() {
    return restrictedCount;
  }

  public int getBannedCount() {
    return bannedCount;
  }

  public boolean isCanGetMembers() {
    return canGetMembers;
  }

  public boolean isCanSetUsername() {
    return canSetUsername;
  }

  public boolean isCanSetStickerSet() {
    return canSetStickerSet;
  }

  public boolean isAllHistoryAvailable() {
    return isAllHistoryAvailable;
  }

  public long getStickerSetId() {
    return stickerSetId;
  }

  public String getInviteLink() {
    return inviteLink;
  }

  public long getPinnedMessageId() {
    return pinnedMessageId;
  }

  public int getUpgradedFromBasicGroupId() {
    return upgradedFromBasicGroupId;
  }

  public long getUpgradedFromMaxMessageId() {
    return upgradedFromMaxMessageId;
  }

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public int getDate() {
    return date;
  }

  public TdApi.ChatMemberStatus getStatus() {
    return status;
  }

  public boolean isAnyoneCanInvite() {
    return anyoneCanInvite;
  }

  public boolean isSignMessages() {
    return signMessages;
  }

  public boolean isChannel() {
    return isChannel;
  }

  public boolean isVerified() {
    return isVerified;
  }

  public String getRestrictionReason() {
    return restrictionReason;
  }

  @Override
  public String toString() {
    return "TDSuperGroup{"
        + "description='"
        + description
        + '\''
        + ", memberCount="
        + memberCount
        + ", administratorCount="
        + administratorCount
        + ", restrictedCount="
        + restrictedCount
        + ", bannedCount="
        + bannedCount
        + ", canGetMembers="
        + canGetMembers
        + ", canSetUsername="
        + canSetUsername
        + ", canSetStickerSet="
        + canSetStickerSet
        + ", isAllHistoryAvailable="
        + isAllHistoryAvailable
        + ", stickerSetId="
        + stickerSetId
        + ", inviteLink='"
        + inviteLink
        + '\''
        + ", pinnedMessageId="
        + pinnedMessageId
        + ", upgradedFromBasicGroupId="
        + upgradedFromBasicGroupId
        + ", upgradedFromMaxMessageId="
        + upgradedFromMaxMessageId
        + ", id="
        + id
        + ", username='"
        + username
        + '\''
        + ", date="
        + date
        + ", status="
        + status
        + ", anyoneCanInvite="
        + anyoneCanInvite
        + ", signMessages="
        + signMessages
        + ", isChannel="
        + isChannel
        + ", isVerified="
        + isVerified
        + ", restrictionReason='"
        + restrictionReason
        + '\''
        + '}';
  }
}
