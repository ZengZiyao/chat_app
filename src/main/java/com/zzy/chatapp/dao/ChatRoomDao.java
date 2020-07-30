package com.zzy.chatapp.dao;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chat_room")
public class ChatRoomDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chat_room_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private long passcode;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDao owner;

    @ManyToMany(mappedBy = "joinedChatRooms", cascade = CascadeType.ALL)
    private Set<UserDao> members;

    public ChatRoomDao(String name, UserDao owner) {
        this.name = name;
        this.owner = owner;
    }
}
