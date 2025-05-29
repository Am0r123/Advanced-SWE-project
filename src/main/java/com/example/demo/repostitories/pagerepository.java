package com.example.demo.repostitories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.page;

public interface pagerepository extends JpaRepository<page, Long> {
    List<page> findByRequiresLogin(Boolean requiresLogin);
}