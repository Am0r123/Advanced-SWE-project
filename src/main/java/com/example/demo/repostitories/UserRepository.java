package com.example.demo.repostitories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.User;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
