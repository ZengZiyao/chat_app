package com.zzy.chatapp.service;

import com.zzy.chatapp.common.utils.JwtTokenUtil;
import com.zzy.chatapp.dao.UserDao;
import com.zzy.chatapp.dto.UserDto;
import com.zzy.chatapp.exception.BadCredentialsException;
import com.zzy.chatapp.exception.DuplicateException;
import com.zzy.chatapp.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil, AppUserDetailsService AppUserDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = AppUserDetailsService;
    }

    public void signUp(UserDto userDto) {

        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new DuplicateException("username already exists: " + userDto.getUsername());
        } else {


            UserDao userDao = new UserDao();
            userDao.setUsername(userDto.getUsername());
            userDao.setPassword(passwordEncoder.encode(userDto.getPassword()));

            userRepository.save(userDao);
        }

    }

    public String signIn(UserDto userDto) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        if (!passwordEncoder.matches(userDto.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("username or password does not match");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return jwtTokenUtil.generateToken(userDetails);

    }
}
