package com.zzy.chatapp.dao;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_detail")
public class UserDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Setter
    @NotNull
    private String username;

    @Setter
    @NotNull
    private String password;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<ChatRoomDao> myChatRooms;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_chat_room_relationship",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_room_id"))
    private Set<ChatRoomDao> joinedChatRooms;

}
