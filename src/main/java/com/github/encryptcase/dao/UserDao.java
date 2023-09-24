package com.github.encryptcase.dao;

import com.github.encryptcase.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Integer> {
}
