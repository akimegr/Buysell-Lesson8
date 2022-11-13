package com.shop.engine.repositories;

import com.shop.engine.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findByActive(boolean a);
    User findByPhoneNumber(String phoneNumber);
}
