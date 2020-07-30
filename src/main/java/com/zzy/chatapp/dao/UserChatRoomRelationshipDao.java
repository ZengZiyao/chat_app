//package com.zzy.chatapp.dao;
//
//import com.sun.istack.NotNull;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Entity
//@Table(name = "user_chatroom_relationship", uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "chatRoomId"}))
//public class UserChatRoomRelationshipDao {
//
//    @Id
//    private Long id;
//
//    @NotNull
//    private Long userId;
//
//    @NotNull
//    private Long chatRoomId;
//
//    public UserChatRoomRelationshipDao(Long userId, Long chatRoomId) {
//        this.userId = userId;
//        this.chatRoomId = chatRoomId;
//    }
//
//    public UserChatRoomRelationshipDao(Long chatRoomId) {
//        this.chatRoomId = chatRoomId;
//    }
//}
