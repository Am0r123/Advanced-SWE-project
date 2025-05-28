package com.example.demo.repostitories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Event;

public interface eventrepository extends JpaRepository<Event, Integer> {
    
}
//The repository in Spring Boot is very important because it acts as a bridge between your application and the database.