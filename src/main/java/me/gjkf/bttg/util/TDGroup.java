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

import org.drinkless.tdlib.TdApi;

import java.util.Arrays;

public class TDGroup {

  private final int creatorUserId;
  private final TdApi.ChatMember[] members;
  private final String inviteLink;
  private final int id;
  private final int memberCount;
  private final TdApi.ChatMemberStatus status;
  private final boolean everyoneIsAdministrator;
  private final boolean isActive;
  private final int upgradedToSupergroupId;

  public TDGroup(
      int creatorUserId,
      TdApi.ChatMember[] members,
      String inviteLink,
      int id,
      int memberCount,
      TdApi.ChatMemberStatus status,
      boolean everyoneIsAdministrator,
      boolean isActive,
      int upgradedToSupergroupId) {
    this.creatorUserId = creatorUserId;
    this.members = members;
    this.inviteLink = inviteLink;
    this.id = id;
    this.memberCount = memberCount;
    this.status = status;
    this.everyoneIsAdministrator = everyoneIsAdministrator;
    this.isActive = isActive;
    this.upgradedToSupergroupId = upgradedToSupergroupId;
  }

  public int getCreatorUserId() {
    return creatorUserId;
  }

  public TdApi.ChatMember[] getMembers() {
    return members;
  }

  public String getInviteLink() {
    return inviteLink;
  }

  public int getId() {
    return id;
  }

  public int getMemberCount() {
    return memberCount;
  }

  public TdApi.ChatMemberStatus getStatus() {
    return status;
  }

  public boolean isEveryoneIsAdministrator() {
    return everyoneIsAdministrator;
  }

  public boolean isActive() {
    return isActive;
  }

  public int getUpgradedToSupergroupId() {
    return upgradedToSupergroupId;
  }

  @Override
  public String toString() {
    return "TDGroup{" +
        "creatorUserId=" + creatorUserId +
        ", members=" + Arrays.toString(members) +
        ", inviteLink='" + inviteLink + '\'' +
        ", id=" + id +
        ", memberCount=" + memberCount +
        ", status=" + status +
        ", everyoneIsAdministrator=" + everyoneIsAdministrator +
        ", isActive=" + isActive +
        ", upgradedToSupergroupId=" + upgradedToSupergroupId +
        '}';
  }
}
