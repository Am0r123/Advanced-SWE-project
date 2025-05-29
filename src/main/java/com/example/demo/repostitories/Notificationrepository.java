package com.example.demo.repostitories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Notification;

public interface Notificationrepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    long countByUserIdAndIsReadFalse(Long userId);
}
