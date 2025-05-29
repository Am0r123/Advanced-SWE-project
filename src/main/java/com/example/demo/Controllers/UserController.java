package com.example.Quiz.demo.controllers;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.example.Quiz.demo.models.User;
import com.example.Quiz.demo.repositories.UserRepository;
import com.example.Quiz.demo.repositories.adminrepository;
import com.example.Quiz.demo.models.Admin;

import org.mindrot.jbcrypt.BCrypt;


import com.example.Quiz.demo.services.UserService;

import jakarta.servlet.http.HttpSession;



@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private adminrepository adminrepository;





    @GetMapping("Register")
   public ModelAndView addUser(@RequestParam(value = "success", required = false) String success,
   @RequestParam(value = "error", required = false) String error) {
    ModelAndView mav = new ModelAndView("Register.html");
            if (success != null) {
                mav.addObject("success", success);
            }
        
            if (error != null) {
                mav.addObject("error", error);
            }
            User newUser = new User();
            mav.addObject("user", newUser);
            return mav;
}

   @PostMapping("Register")
   public String saveUser(@ModelAttribute User user) {



    String encodedPassword = BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(12));
    user.setPassword(encodedPassword);
    this.userService.save(user);
        return "<script>window.location.href='/home?success=Successfully Registered';</script>";
    
}
@GetMapping("Login")
public ModelAndView Login(@RequestParam(value = "success", required = false) String success,
    @RequestParam(value = "error", required = false) String error) {
    ModelAndView mav = new ModelAndView("Login");
        if (success != null) {
            mav.addObject("success", success);
        }
    
        if (error != null) {
            mav.addObject("error", error);
        }
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
}

    @PostMapping("Login")
    public String LoginProcess(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        System.out.println("Login attempt: " + username);

        User dbUser = this.userRepository.findByUsername(username);

        if (dbUser != null) {
            System.out.println("Login attempt: " + dbUser);
            System.out.println("Login attempt: " + password);
            System.out.println("Login attempt: " + password);

            Boolean isPasswordMatched = BCrypt.checkpw(password, dbUser.getPassword());
            if (isPasswordMatched) {
                System.out.println("Login attempt: " + dbUser);
                

                session.setAttribute("isLoggedIn", true);
                session.setAttribute("UserId", dbUser.getId());
                return "<script>window.location.href='/home?success=Welcome " + dbUser.getName() + "';</script>";
            }
        }
    
        Admin dbAdmin = this.adminrepository.findByUsername(username);
        
        if (dbAdmin != null) {
            Boolean isPasswordMatched = BCrypt.checkpw(password, dbAdmin.getPassword());
            if (isPasswordMatched) {
                return "<script>window.location.href='/admin/dashboard?success=Welcome Admin " + dbAdmin.getName() + "';</script>";
            }
        }
    
        return "<script>window.location.href='/User/Login?error=Invalid Username or Password';</script>";
    }    
}

