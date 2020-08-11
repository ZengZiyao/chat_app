package com.zzy.chatapp.repository;

import com.zzy.chatapp.dao.ChatRoomDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomDao, Long> {

    ChatRoomDao findChatRoomDaoByPasscode(long passcode);
}
