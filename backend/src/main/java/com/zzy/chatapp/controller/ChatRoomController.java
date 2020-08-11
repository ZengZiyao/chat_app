package com.zzy.chatapp.controller;

import com.zzy.chatapp.dto.ChatRoomDetailsDto;
import com.zzy.chatapp.dto.ChatRoomResponseDto;
import com.zzy.chatapp.exception.BadRequestException;
import com.zzy.chatapp.service.ChatRoomService;
import com.zzy.chatapp.service.impl.ChatRoomServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/chatroom")
public class ChatRoomController {

    private ChatRoomService chatRoomService;
    private SimpMessagingTemplate simpMessagingTemplate;

    public ChatRoomController(ChatRoomServiceImpl chatRoomService, SimpMessagingTemplate simpMessagingTemplate) {
        this.chatRoomService = chatRoomService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostMapping("")
    public ResponseEntity createChatRoom(@RequestBody ChatRoomDetailsDto chatRoomDetailsDto, HttpServletRequest httpServletRequest) {
        if (chatRoomDetailsDto.getName() == null || chatRoomDetailsDto.getName() == "") {
            throw new BadRequestException("Bad attribute value");
        }
        String username = httpServletRequest.getUserPrincipal().getName();
        long passcode = chatRoomService.createChatRoom(chatRoomDetailsDto, username);

        Map<String, Long> response = new HashMap<>();
        response.put("passcode", passcode);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping("/by-user")
    public ResponseEntity getMyChatRooms(HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getUserPrincipal().getName();

        Set<ChatRoomResponseDto> chatRoomResponseDtoSet = chatRoomService.getChatRoomsByUser(username);

        Map<String, Set> response = new HashMap<>();

        response.put("chatRooms", chatRoomResponseDtoSet);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity deleteChatRoom(@PathVariable Long chatRoomId, HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getUserPrincipal().getName();

        chatRoomService.deleteChatRoom(username, chatRoomId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity getChatRoomById(@PathVariable Long chatRoomId) {

        ChatRoomResponseDto chatRoomResponseDto = chatRoomService.getChatRoomById(chatRoomId);
        return ResponseEntity.ok(chatRoomResponseDto);
    }


}
