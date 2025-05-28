package com.example.demo.Controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.models.Event;
import com.example.demo.models.Notification;
import com.example.demo.models.Task;
import com.example.demo.repostitories.eventrepository;
import com.example.demo.repostitories.taskrepository;

import jakarta.servlet.http.HttpSession;

import com.example.demo.repostitories.Notificationrepository;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/issues")
public class TaskEventController {

    @Autowired
    private taskrepository taskRepository;

    @Autowired
    private eventrepository eventRepository;

    @Autowired
    private Notificationrepository notificationRepository;

    
    private final HttpSession session;

    public TaskEventController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/create")
    public String showCreateForm(@RequestParam(name = "selectedDate", required = false) String selectedDate,
                                 @RequestParam(name = "type", defaultValue = "task") String type,
                                 Model model) {//Gets a URL parameter called type,If it's not provided, the default value "task" is used.
                                 //
        if (type.equals("task")) {
            Task task = new Task();
            if (selectedDate != null) {
                LocalDate date = LocalDate.parse(selectedDate);
                task.setStartDate(date);/*If a date was selected, it converts the string to a LocalDate and sets 
                                         it as both start and end date for the task.*/
                task.setEndDate(date);
            }
            model.addAttribute("task", task);
            model.addAttribute("mode", "task");//Sends the task and "task" mode to the HTML page via the model.
        } else {
            Event event = new Event();
            if (selectedDate != null) {
                LocalDate date = LocalDate.parse(selectedDate);
                event.setEventDate(date);
                model.addAttribute("selectedDate", selectedDate);/*If a date is selected, it sets the event's
                 date and also sends that selected date to the view.javaCopyEdit*/
            }
            model.addAttribute("event", event);
            model.addAttribute("mode", "event");
        }
    
        return "Issue";
    }


    @PostMapping("/create-task")
    public String createTask(@ModelAttribute Task task) {
        taskRepository.save(task);

        Object userIdObj = session.getAttribute("UserId");
        if (userIdObj == null) return "redirect:/login";

        Long userId = (userIdObj instanceof Integer)
                ? ((Integer) userIdObj).longValue()
                : (Long) userIdObj;

        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage("New task created: " + task.getTitle());
        notification.setRead(false);
        notification.setDate(LocalDate.now());
        notificationRepository.save(notification);

        return "redirect:/timeline";
    }

    @PostMapping("/create-event")
    public String createEvent(@ModelAttribute Event event) { //(ModelAttribute)bt3mel atrribute global ll project 
        eventRepository.save(event);//bt save fe el database 

        Object userIdObj = session.getAttribute("UserId");//Retrieves UserId from the current session.
        if (userIdObj == null) return "redirect:/login";//If no user is logged in, redirects to the login page.

        Long userId = (userIdObj instanceof Integer)
                ? ((Integer) userIdObj).longValue()
                : (Long) userIdObj;//Converts the session UserId safely to Long, handling both Integer and Long cases.
                //7sal error 3malna quick fix 

        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage("New event created: " + event.getTitle());
        notification.setRead(false);//el user 2ara el notofication wala laa 
        notification.setDate(LocalDate.now());
        notificationRepository.save(notification);

        return "redirect:/calendar";
    }
}