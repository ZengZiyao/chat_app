package com.zzy.chatapp.service;

import com.zzy.chatapp.dto.UserDto;

public interface UserService {


    void signUp(UserDto userDto);

    String signIn(UserDto userDto);
}
