package com.zzy.chatapp.service;

import com.zzy.chatapp.dto.ChatRoomDetailsDto;
import com.zzy.chatapp.dto.ChatRoomResponseDto;

import java.util.Set;

public interface ChatRoomService {


    long createChatRoom(ChatRoomDetailsDto chatRoomDetailsDto, String username);

    ChatRoomResponseDto joinChatRoom(long passcode, String username);

    Set<ChatRoomResponseDto> getChatRoomsByUser(String username);

    boolean deleteChatRoom(String username, Long chatRoomId);

    boolean leaveChatRoom(String username, Long chatRoomId);

    public ChatRoomResponseDto getChatRoomById(Long chatRoomId);


}
