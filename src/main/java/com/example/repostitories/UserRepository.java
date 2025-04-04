package com.example.Quiz.demo.repostitories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Quiz.demo.models.User;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
