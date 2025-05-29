package com.example.Quiz.user_microservice.repostitories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Quiz.user_microservice.models.User;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
