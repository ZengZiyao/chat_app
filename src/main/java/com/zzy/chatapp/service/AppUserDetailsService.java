package com.zzy.chatapp.service;

import com.zzy.chatapp.dao.UserDao;
import com.zzy.chatapp.model.AppUserDetails;
import com.zzy.chatapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDao userDao = userRepository.findByUsername(s);

        return new AppUserDetails(userDao.getUsername(), userDao.getPassword());
    }


}
