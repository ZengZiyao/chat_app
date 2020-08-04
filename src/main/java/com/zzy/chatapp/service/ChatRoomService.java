package com.zzy.chatapp.service;

import com.zzy.chatapp.dto.ChatRoomDetailsDto;
import com.zzy.chatapp.dto.ChatRoomResponse;

import java.util.Set;

public interface ChatRoomService {


    long createChatRoom(ChatRoomDetailsDto chatRoomDetailsDto, String username);

    ChatRoomResponse joinChatRoom(long passcode, String username);

    Set<ChatRoomResponse> getChatRoomsByUser(String username);

    boolean deleteChatRoom(String username, Long chatRoomId);

    boolean leaveChatRoom(String username, Long chatRoomId);


}
