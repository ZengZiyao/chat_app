package com.zzy.chatapp.model;

import com.zzy.chatapp.dao.UserDao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomMember {

    private long id;

    private String username;

    public static ChatRoomMember fromUserDao(UserDao userDao) {
        return new ChatRoomMember(userDao.getId(), userDao.getUsername());
    }
}
