package com.example.repostitories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.models.User;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
