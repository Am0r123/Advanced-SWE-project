package com.example.demo.repostitories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Admin;

public interface adminrepository extends JpaRepository<Admin,Integer> {
    
    Admin findByUsername(String username);
}
