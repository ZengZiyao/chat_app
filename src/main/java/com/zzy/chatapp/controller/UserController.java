package com.zzy.chatapp.controller;

import com.zzy.chatapp.dto.UserDto;
import com.zzy.chatapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserDto userDto) {
        userService.signUp(userDto);
        return new ResponseEntity("User account created", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity signin(@RequestBody UserDto userDto) {
        String token = userService.signIn(userDto);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return ResponseEntity.ok(tokenMap);
    }
}
