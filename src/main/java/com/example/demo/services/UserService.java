package com.example.Quiz.demo.services;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.Quiz.demo.models.User;

@Service
public class UserService {

    private RestTemplate restTemplate;
    private String baseUrl = "http://localhost:8081"; // Base URL for the REST API

    public UserService() {
        this.restTemplate = new RestTemplate(); // Initialize a new RestTemplate instance
    }
    public void save(User user) {
        String url = baseUrl + "/User/Register"; // The endpoint URL for saving a post
        this.restTemplate.postForObject(url, user, User.class);
    }
        public User findByUsername(String username) {
        String url = baseUrl + "/User/find?username=" + username;
        return restTemplate.getForObject(url, User.class);
    }
}
