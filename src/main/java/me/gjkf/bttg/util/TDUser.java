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

public class TDUser {

  private final boolean isBlocked;
  private final boolean canBeCalled;
  private final boolean hasPrivateCalls;
  private final String bio;
  private final String shareText;
  private final int groupInCommonCount;
  private final TdApi.BotInfo botInfo;
  private final int id;
  private final String firstName;
  private final String lastName;
  private final String username;
  private final String phoneNumber;
  private final TdApi.UserStatus status;
  private final TdApi.ProfilePhoto profilePhoto;
  private final TdApi.LinkState outgoingLink;
  private final TdApi.LinkState incomingLink;
  private final boolean isVerified;
  private final String restrictionReason;
  private final boolean haveAccess;
  private final TdApi.UserType type;
  private final String languageCode;

  public TDUser(
      boolean isBlocked,
      boolean canBeCalled,
      boolean hasPrivateCalls,
      String bio,
      String shareText,
      int groupInCommonCount,
      TdApi.BotInfo botInfo,
      int id,
      String firstName,
      String lastName,
      String username,
      String phoneNumber,
      TdApi.UserStatus status,
      TdApi.ProfilePhoto profilePhoto,
      TdApi.LinkState outgoingLink,
      TdApi.LinkState incomingLink,
      boolean isVerified,
      String restrictionReason,
      boolean haveAccess,
      TdApi.UserType type,
      String languageCode) {
    this.isBlocked = isBlocked;
    this.canBeCalled = canBeCalled;
    this.hasPrivateCalls = hasPrivateCalls;
    this.bio = bio;
    this.shareText = shareText;
    this.groupInCommonCount = groupInCommonCount;
    this.botInfo = botInfo;
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.phoneNumber = phoneNumber;
    this.status = status;
    this.profilePhoto = profilePhoto;
    this.outgoingLink = outgoingLink;
    this.incomingLink = incomingLink;
    this.isVerified = isVerified;
    this.restrictionReason = restrictionReason;
    this.haveAccess = haveAccess;
    this.type = type;
    this.languageCode = languageCode;
  }

  public boolean isBlocked() {
    return isBlocked;
  }

  public boolean isCanBeCalled() {
    return canBeCalled;
  }

  public boolean isHasPrivateCalls() {
    return hasPrivateCalls;
  }

  public String getBio() {
    return bio;
  }

  public String getShareText() {
    return shareText;
  }

  public int getGroupInCommonCount() {
    return groupInCommonCount;
  }

  public TdApi.BotInfo getBotInfo() {
    return botInfo;
  }

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getUsername() {
    return username;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public TdApi.UserStatus getStatus() {
    return status;
  }

  public TdApi.ProfilePhoto getProfilePhoto() {
    return profilePhoto;
  }

  public TdApi.LinkState getOutgoingLink() {
    return outgoingLink;
  }

  public TdApi.LinkState getIncomingLink() {
    return incomingLink;
  }

  public boolean isVerified() {
    return isVerified;
  }

  public String getRestrictionReason() {
    return restrictionReason;
  }

  public boolean isHaveAccess() {
    return haveAccess;
  }

  public TdApi.UserType getType() {
    return type;
  }

  public String getLanguageCode() {
    return languageCode;
  }

  @Override
  public String toString() {
    return "TDUser{" +
        "isBlocked=" + isBlocked +
        ", canBeCalled=" + canBeCalled +
        ", hasPrivateCalls=" + hasPrivateCalls +
        ", bio='" + bio + '\'' +
        ", shareText='" + shareText + '\'' +
        ", groupInCommonCount=" + groupInCommonCount +
        ", botInfo=" + botInfo +
        ", id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", username='" + username + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", status=" + status +
        ", profilePhoto=" + profilePhoto +
        ", outgoingLink=" + outgoingLink +
        ", incomingLink=" + incomingLink +
        ", isVerified=" + isVerified +
        ", restrictionReason='" + restrictionReason + '\'' +
        ", haveAccess=" + haveAccess +
        ", type=" + type +
        ", languageCode='" + languageCode + '\'' +
        '}';
  }
}
