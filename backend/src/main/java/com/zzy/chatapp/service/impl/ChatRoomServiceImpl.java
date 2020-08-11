package com.zzy.chatapp.service.impl;

import com.zzy.chatapp.dao.ChatRoomDao;
import com.zzy.chatapp.dao.UserDao;
import com.zzy.chatapp.dto.ChatRoomDetailsDto;
import com.zzy.chatapp.dto.ChatRoomResponseDto;
import com.zzy.chatapp.exception.ForbiddenException;
import com.zzy.chatapp.exception.NotFoundException;
import com.zzy.chatapp.repository.ChatRoomRepository;
import com.zzy.chatapp.repository.UserRepository;
import com.zzy.chatapp.service.ChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService {
    private static final int MIN_PASSCODE = 100000;

    private ChatRoomRepository chatRoomRepository;
    private UserDetailsServiceImpl userDetailsService;
    private UserRepository userRepository;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository, UserDetailsServiceImpl userDetailsService, UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;

    }

    @Transactional
    public long createChatRoom(ChatRoomDetailsDto chatRoomDetailsDto, String username) {
        Long userId = userDetailsService.getUserIdByUsername(username);

        UserDao userDao = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User does not exist: " + userId));
        ChatRoomDao chatRoomDao = new ChatRoomDao(chatRoomDetailsDto.getName(), userDao);
        chatRoomRepository.save(chatRoomDao);
        long passcode = MIN_PASSCODE + chatRoomDao.getId();
        chatRoomDao.setPasscode(passcode);
        return passcode;
    }

    @Transactional
    public ChatRoomResponseDto joinChatRoom(long passcode, String username) {
        Long userId = userDetailsService.getUserIdByUsername(username);
        ChatRoomDao chatRoomDao = chatRoomRepository.findChatRoomDaoByPasscode(passcode);

        if (chatRoomDao != null) {

            UserDao userDao = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User does not exist: " + userId));

            userDao.getJoinedChatRooms().add(chatRoomDao);

            return ChatRoomResponseDto.fromChatRoomDao(chatRoomDao);
        }

        throw new NotFoundException("ChatRoom does not exist with passcode: " + passcode);

    }

    @Transactional
    public Set<ChatRoomResponseDto> getChatRoomsByUser(String username) {

        Set<ChatRoomDao> chatRoomDaoSet = userRepository.findByUsername(username).getMyChatRooms();
        Set<ChatRoomResponseDto> chatRoomResponseDtoSet = new HashSet<>();
        chatRoomDaoSet.forEach(chatRoomDao -> chatRoomResponseDtoSet.add(ChatRoomResponseDto.fromChatRoomDao(chatRoomDao)));

        return chatRoomResponseDtoSet;
    }

    public boolean deleteChatRoom(String username, Long chatRoomId) {

        ChatRoomDao chatRoomDao = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new NotFoundException("ChatRoom does not exist: " + chatRoomId));
        if (chatRoomDao.getOwner().getUsername().equals(username)) {
            chatRoomRepository.deleteById(chatRoomId);
            return true;
        }

        throw new ForbiddenException("Forbidden to delete chatRoom: " + chatRoomId);
    }

    @Transactional
    public boolean leaveChatRoom(String username, Long chatRoomId) {

        UserDao userDao = userRepository.findByUsername(username);

        if (userDao != null) {
            Set<ChatRoomDao> chatRoomDaoSet = userDao.getJoinedChatRooms();

            chatRoomDaoSet.removeIf((chatRoomDao -> chatRoomDao.getId() == chatRoomId));
            return true;
        }

        throw new NotFoundException("User does not exist: " + username);
    }

    public ChatRoomResponseDto getChatRoomById(Long chatRoomId) {
        ChatRoomDao chatRoomDao = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new NotFoundException("ChatRoom not found with id: " + chatRoomId));

        return ChatRoomResponseDto.fromChatRoomDao(chatRoomDao);
    }

}
