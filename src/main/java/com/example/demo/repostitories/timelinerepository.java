package com.example.demo.repostitories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.timeline;

public interface timelinerepository extends JpaRepository<timeline,Integer> {

}