package com.example.Quiz.user_microservice.controllers;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.example.Quiz.user_microservice.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;


import com.example.Quiz.user_microservice.repostitories.UserRepository;

import jakarta.servlet.http.HttpSession;



@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserRepository userRepository;  


   @PostMapping("Register")
    public ResponseEntity createPost(@RequestBody Map<String,String> body){
        
        User myUser = new User(); 
            System.out.println("Saving user: " + myUser.getUsername());

        myUser.setName(body.get("name"));
        myUser.setUsername(body.get("username"));
        myUser.setPassword(body.get("password"));

        // String hashedPassword = BCrypt.hashpw(body.get("password"), BCrypt.gensalt(12));
        // myUser.setPassword(hashedPassword);

        this.userRepository.save(myUser); 

        return new ResponseEntity(myUser, HttpStatus.CREATED);
    }

        @GetMapping("find")
    public ResponseEntity<User> findByUsername(@RequestParam String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}

