package com.zzy.chatapp.service.impl;

import com.zzy.chatapp.dao.ChatRoomDao;
import com.zzy.chatapp.dao.UserDao;
import com.zzy.chatapp.dto.ChatRoomDetailsDto;
import com.zzy.chatapp.dto.ChatRoomResponse;
import com.zzy.chatapp.exception.ForbiddenException;
import com.zzy.chatapp.exception.NotFoundException;
import com.zzy.chatapp.repository.ChatRoomRepository;
import com.zzy.chatapp.repository.UserRepository;
import com.zzy.chatapp.service.ChatRoomService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    private static final int MIN_PASSCODE = 100000;

    private  ChatRoomRepository chatRoomRepository;
    private  UserDetailsServiceImpl userDetailsService;
    private  UserRepository userRepository;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository, UserDetailsServiceImpl userDetailsService, UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;

    }

    public long createChatRoom(ChatRoomDetailsDto chatRoomDetailsDto, String username) {
        Long userId = userDetailsService.getUserIdByUsername(username);

        UserDao userDao = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User does not exist: " + userId));
        ChatRoomDao chatRoomDao = new ChatRoomDao(chatRoomDetailsDto.getName(), userDao);
        chatRoomRepository.save(chatRoomDao);
        long passcode = MIN_PASSCODE + chatRoomDao.getId();
        chatRoomDao.setPasscode(passcode);

        return passcode;
    }

    public ChatRoomResponse joinChatRoom(long passcode, String username) {
        Long userId = userDetailsService.getUserIdByUsername(username);
        ChatRoomDao chatRoomDao = chatRoomRepository.findChatRoomDaoByPasscode(passcode);

        if (chatRoomDao != null) {

            UserDao userDao = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User does not exist: " + userId));

            userDao.getJoinedChatRooms().add(chatRoomDao);

            return ChatRoomResponse.fromChatRoomDao(chatRoomDao);
        }

        throw new NotFoundException("ChatRoom does not exist with passcode: " + passcode);

    }

    public Set<ChatRoomResponse> getChatRoomsByUser(String username) {

        Set<ChatRoomDao> chatRoomDaoSet = userRepository.findByUsername(username).getMyChatRooms();
        Set<ChatRoomResponse> chatRoomResponseSet = new HashSet<>();
        chatRoomDaoSet.forEach(chatRoomDao -> chatRoomResponseSet.add(ChatRoomResponse.fromChatRoomDao(chatRoomDao)));

        return chatRoomResponseSet;
    }

    public boolean deleteChatRoom(String username, Long chatRoomId) {

        ChatRoomDao chatRoomDao = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new NotFoundException("ChatRoom does not exist: " + chatRoomId));
        if (chatRoomDao.getOwner().getUsername().equals(username)) {
            chatRoomRepository.deleteById(chatRoomId);
            return true;
        }

        throw new ForbiddenException("Forbidden to delete chatRoom: " + chatRoomId);
    }

    public boolean leaveChatRoom(String username, Long chatRoomId) {

        UserDao userDao = userRepository.findByUsername(username);

        if (userDao != null) {
            Set<ChatRoomDao> chatRoomDaoSet = userDao.getJoinedChatRooms();

            chatRoomDaoSet.removeIf((chatRoomDao -> chatRoomDao.getId() == chatRoomId));
            return true;
        }

        throw new NotFoundException("User does not exist: " + username);
    }

}
