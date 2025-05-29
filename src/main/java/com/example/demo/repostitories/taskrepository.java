package com.example.demo.repostitories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Task;

public interface taskrepository extends JpaRepository<Task, Integer> {
    
}
