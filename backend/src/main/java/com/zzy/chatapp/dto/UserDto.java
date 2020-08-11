package com.zzy.chatapp.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

}
