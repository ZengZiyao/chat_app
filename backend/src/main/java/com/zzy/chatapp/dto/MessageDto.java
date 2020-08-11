package com.zzy.chatapp.dto;

import com.zzy.chatapp.common.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class MessageDto implements Serializable {
    private MessageType type;
    private String content;
    private String sender;
    private Long chatRoomId;
}
