package com.example.demo.Controllers;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.models.User;
import com.example.demo.models.Admin;
import com.example.demo.models.Event;
import com.example.demo.models.Notification;
import com.example.demo.models.Task;
import com.example.demo.repostitories.UserRepository;
import com.example.demo.repostitories.adminrepository;
import com.example.demo.repostitories.Notificationrepository;
import com.example.demo.repostitories.eventrepository;
import com.example.demo.repostitories.taskrepository;

@Controller
@RequestMapping("/admin")
public class adminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private adminrepository adminRepository;
    @Autowired
    private Notificationrepository Notificationrepository;
    @Autowired
    private eventrepository eventrepository;
    @Autowired
    private taskrepository taskrepository;
    @GetMapping("/dashboard")
    public ModelAndView dasboard() {
        ModelAndView mav = new ModelAndView("admin.html");
        List<Event> events = eventrepository.findAll();
        List<Task> tasks = taskrepository.findAll();
        List<Notification> notifications = Notificationrepository.findAll();
        mav.addObject("users", userRepository.findAll());
        mav.addObject("admins", adminRepository.findAll());
        mav.addObject("events", events);
        mav.addObject("tasks", tasks);
        mav.addObject("notifications", notifications);

        mav.addObject("totalUsers", userRepository.count());
        mav.addObject("activeMemberships", 120);
        mav.addObject("monthlyRevenue", "$8,200");
        mav.addObject("productsSold", 75);

        return mav;
    }

    @PostMapping("/add-user")
    public String addUser(@ModelAttribute User user) {
        User existingUser = this.userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            return "redirect:/admin/dashboard?error=Username Already Exists";    
        }

        String encodedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(encodedPassword);
        this.userRepository.save(user);
        return "redirect:/admin/dashboard?success=Successfully Registered";   
    }

    @PostMapping("/add-admin")
    public String addAdmin(@ModelAttribute Admin admin) {
        try {
            if (admin == null) {
                return "redirect:/admin/dashboard?error=Failed to Add Admin";
            }

            String encodedPassword = BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt(12));
            admin.setPassword(encodedPassword);
            adminRepository.save(admin);
            return "redirect:/admin/dashboard?success=Admin Added Successfully";
        } catch (Exception e) {
            return "redirect:/admin/dashboard?error=Failed to Add Admin";
        }
    }

    @GetMapping("/edit-user/{id}")
    public String getEditUserPage(@PathVariable Integer id, Model model) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        model.addAttribute("user", user);
        
        return "edituser";
    }
    

    @PostMapping("/edit-user")
    public String editUser(@ModelAttribute User user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        existingUser.setName(user.getName());
        existingUser.setUsername(user.getUsername());
        
        userRepository.save(existingUser);
        
        return "redirect:/admin/dashboard?success=User Updated Successfully";
    }


    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam Integer id) {
        this.userRepository.deleteById(id);
        return "redirect:/admin/dashboard?success=User Deleted Successfully";
    }
    @GetMapping("/edit-admin/{id}")
    public String getEditAdminPage(@PathVariable Integer id, Model model) {
        Admin admin = this.adminRepository.findById(id)
                                  .orElseThrow(() -> new IllegalArgumentException("Admin not found"));
        
        model.addAttribute("admin", admin);
        
        return "editadmin";
    }
    

    @PostMapping("/edit-admin")
    public String editAdmin(@ModelAttribute Admin admin) {
        Admin existingUser = this.adminRepository.findById(admin.getId()).orElseThrow(() -> new IllegalArgumentException("Admin not found"));
        
        existingUser.setName(admin.getName());
        existingUser.setUsername(admin.getUsername());
        
        adminRepository.save(existingUser);
        
        return "redirect:/admin/dashboard?success=Admin Updated Successfully";
    }

    @PostMapping("/delete-admin")
    public String deleteAdmin(@RequestParam Integer id) {
        this.adminRepository.deleteById(id);
        return "redirect:/admin/dashboard?success=Admin Deleted Successfully";
    }
    @PostMapping("/add-event")
    public String addEvent(@ModelAttribute Event event) {
        eventrepository.save(event);
        return "redirect:/admin/dashboard?success=Event Added Successfully";
    }

    @GetMapping("/edit-event/{id}")
    public String editEventForm(@PathVariable Integer id, Model model) {
        Event event = eventrepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        model.addAttribute("event", event);
        return "editevent";
    }

    @PostMapping("/edit-event")
    public String editEvent(@ModelAttribute Event event) {
        eventrepository.save(event);
        return "redirect:/admin/dashboard?success=Event Updated Successfully";
    }
    @PostMapping("/delete-event")
    public String deleteEvent(@RequestParam Integer id) {
        eventrepository.deleteById(id);
        return "redirect:/admin/dashboard?success=Event Deleted Successfully";
    }

    @PostMapping("/add-task")
    public String addTask(@ModelAttribute Task task) {
        taskrepository.save(task);
        return "redirect:/admin/dashboard?success=Task Added Successfully";
    }

    @GetMapping("/edit-task/{id}")
    public String editTaskForm(@PathVariable Integer id, Model model) {
        Task task = taskrepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        model.addAttribute("task", task);
        return "edittask";
    }

    @PostMapping("/edit-task")
    public String editTask(@ModelAttribute Task task) {
        taskrepository.save(task);
        return "redirect:/admin/dashboard?success=Task Updated Successfully";
    }

    @PostMapping("/delete-task")
    public String deleteTask(@RequestParam Integer id) {
        taskrepository.deleteById(id);
        return "redirect:/admin/dashboard?success=Task Deleted Successfully";
    }

    @PostMapping("/add-notification")
    public String addNotification(@RequestParam String message,
                                  @RequestParam String date,
                                  @RequestParam Integer userId) {
        LocalDate localDate = LocalDate.parse(date);
        Notification notif = new Notification();
        notif.setMessage(message);
        notif.setDate(localDate);
        User user = userRepository.findById(userId)
               .orElseThrow(() -> new IllegalArgumentException("User not found"));
               notif.setUserId((long) user.getId());
        notif.setRead(false);
        Notificationrepository.save(notif);
        return "redirect:/admin/dashboard?success=Notification Added Successfully";
    }
    @GetMapping("/edit-notification/{id}")
    public String editNotificationForm(@PathVariable Long id, Model model) {
        Notification notification = Notificationrepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
        model.addAttribute("notification", notification);
        model.addAttribute("users", userRepository.findAll());
        return "editnotification";
    }

    @PostMapping("/edit-notification")
    public String editNotification(@ModelAttribute Notification notification) {
        Notificationrepository.save(notification);
        return "redirect:/admin/dashboard?success=Notification Updated Successfully";
    }

    @PostMapping("/delete-notification")
    public String deleteNotification(@RequestParam Long id) {
        Notificationrepository.deleteById(id);
        return "redirect:/admin/dashboard?success=Event Deleted Successfully";
    }
}