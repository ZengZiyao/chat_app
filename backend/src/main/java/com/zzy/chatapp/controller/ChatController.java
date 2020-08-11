package com.zzy.chatapp.controller;

import com.zzy.chatapp.common.enums.MessageType;
import com.zzy.chatapp.dto.ChatRoomResponseDto;
import com.zzy.chatapp.dto.MessageDto;
import com.zzy.chatapp.service.ChatRoomService;
import com.zzy.chatapp.service.impl.ChatRoomServiceImpl;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {

    private SimpMessagingTemplate simpMessagingTemplate;
    private ChatRoomService chatRoomService;


    public ChatController(SimpMessagingTemplate simpMessagingTemplate, ChatRoomServiceImpl chatRoomService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatRoomService = chatRoomService;
    }

    @MessageMapping("/chat.{chatRoomId}")
    @SendTo("/group.{chatRoomId}")
    public MessageDto sendMessage(@DestinationVariable Long chatRoomId, @Payload MessageDto messageDto) {
        return messageDto;
    }

    @MessageMapping("/chat.join.passcode.{passcode}")
    public MessageDto joinChatRoom(@DestinationVariable Long passcode, Principal principal, SimpMessageHeaderAccessor headerAccessor) {
        String username = principal.getName();
        ChatRoomResponseDto chatRoomResponseDto = chatRoomService.joinChatRoom(passcode, username);
        headerAccessor.getSessionAttributes().put("username", username);
        Long chatRoomId = chatRoomResponseDto.getId();
        MessageDto messageDto = new MessageDto(MessageType.JOIN, username + " joined", null, chatRoomId);
        simpMessagingTemplate.convertAndSend("/group." + chatRoomId, messageDto);
        return messageDto;
    }

    @MessageMapping("/chat.leave.{chatRoomId}")
    public MessageDto leaveChatRoom(@DestinationVariable Long chatRoomId, Principal principal) {
        String username = principal.getName();

        chatRoomService.leaveChatRoom(username, chatRoomId);
        MessageDto messageDto = new MessageDto(MessageType.LEAVE, username + " left", null, chatRoomId);
        simpMessagingTemplate.convertAndSend("/group." + chatRoomId, messageDto);
        return messageDto;

    }

}
