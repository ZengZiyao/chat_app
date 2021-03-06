package com.zzy.chatapp.controller;

import com.zzy.chatapp.common.utils.JwtTokenUtil;
import com.zzy.chatapp.dto.UserDto;
import com.zzy.chatapp.exception.BadRequestException;
import com.zzy.chatapp.service.UserService;
import com.zzy.chatapp.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;

    public UserController(UserServiceImpl userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserDto userDto) {
        if (userDto.getUsername() == null || userDto.getPassword() == null) {
            throw new BadRequestException("Bad attribute values");
        }
        userService.signUp(userDto);
        return new ResponseEntity("User account created", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity signin(@RequestBody UserDto userDto) {
        if (userDto.getUsername() == null || userDto.getPassword() == null) {
            throw new BadRequestException("Bad attribute values");
        }
        String token = userService.signIn(userDto);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return ResponseEntity.ok(tokenMap);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
        }

        jwtTokenUtil.invalidateToken(jwtTokenUtil.getTokenFromRequest(httpServletRequest));

        return ResponseEntity.ok("Logout out successfully");

    }
}
