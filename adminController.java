package com.example.demo.Controllers;
import org.springframework.ui.Model;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.models.User;
import com.example.demo.models.Admin;
import com.example.demo.repostitories.UserRepository;
import com.example.demo.repostitories.adminrepository;

@Controller
@RequestMapping("/admin")
public class adminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private adminrepository adminRepository;
    @GetMapping("/dashboard")
    public ModelAndView dasboard() {
        ModelAndView mav = new ModelAndView("admin.html");
        mav.addObject("users", userRepository.findAll());
        mav.addObject("admins", adminRepository.findAll());

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
}
