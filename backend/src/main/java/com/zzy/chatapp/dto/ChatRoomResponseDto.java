package com.zzy.chatapp.dto;

import com.zzy.chatapp.dao.ChatRoomDao;
import com.zzy.chatapp.model.ChatRoomMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ChatRoomResponseDto implements Serializable {
    private Long id;

    private long passcode;

    private String name;

    private ChatRoomMember author;

    private Set<ChatRoomMember> memberList;

    public static ChatRoomResponseDto fromChatRoomDao(ChatRoomDao chatRoomDao) {
        Set<ChatRoomMember> chatRoomMembers = new HashSet<>();
        chatRoomDao.getMembers().forEach(userDao -> chatRoomMembers.add(ChatRoomMember.fromUserDao(userDao)));
        return new ChatRoomResponseDto(chatRoomDao.getId(), chatRoomDao.getPasscode(), chatRoomDao.getName(), ChatRoomMember.fromUserDao(chatRoomDao.getOwner()), chatRoomMembers);
    }
}
