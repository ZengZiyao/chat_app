package com.zzy.chatapp.repository;

import com.zzy.chatapp.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDao, Long> {
    UserDao findByUsername(String username);
}
