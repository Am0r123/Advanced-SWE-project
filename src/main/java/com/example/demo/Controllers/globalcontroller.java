package com.example.demo.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.models.page;
import com.example.demo.repostitories.pagerepository;
import com.example.demo.repostitories.Notificationrepository;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@ControllerAdvice
public class globalcontroller {

    @Autowired
    private pagerepository pageRepository;
    @Autowired
    private Notificationrepository notificationRepository;

    @ModelAttribute("pages")
    public List<page> getPages(HttpSession session) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        getUnreadNotificationCount(session);
        if (isLoggedIn != null && isLoggedIn) {
            return pageRepository.findAll();
        } else {
            return pageRepository.findByRequiresLogin(false);
        }
    }
    @ModelAttribute("unreadCount")
    public long getUnreadNotificationCount(HttpSession session) {
        Object userIdObj = session.getAttribute("UserId");

        if (userIdObj == null) return 0L;

        Long userId = (userIdObj instanceof Integer)
            ? ((Integer) userIdObj).longValue()
            : (Long) userIdObj;

        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }
}
