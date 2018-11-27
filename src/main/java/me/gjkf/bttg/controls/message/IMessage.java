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

/**
 * Interface used to organize how a control representing a message should be defined.
 *
 * @author Davide Cossu
 */

public interface IMessage {

  /**
   * Returns the chatId of the message.
   *
   * @return The chatId.
   */
  long getChatId();

  /**
   * Returns the messageId of the message.
   *
   * @return The messageId.
   */
  long getMessageId();

  /**
   * Initialize the control, here should be done the work to set the position, size and style of the
   * controls.
   */
  void initialize();
}
