package com.zzy.chatapp.service.impl;

import com.zzy.chatapp.common.utils.JwtTokenUtil;
import com.zzy.chatapp.dao.UserDao;
import com.zzy.chatapp.dto.UserDto;
import com.zzy.chatapp.exception.BadCredentialsException;
import com.zzy.chatapp.exception.DuplicateException;
import com.zzy.chatapp.repository.UserRepository;
import com.zzy.chatapp.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenUtil jwtTokenUtil;
    private UserDetailsService userDetailsService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil, UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
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

    @Override
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
