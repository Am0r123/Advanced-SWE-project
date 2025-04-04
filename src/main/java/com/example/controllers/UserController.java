package com.example.Quiz.demo.controllers;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.Quiz.demo.models.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.PathVariable;


import com.example.Quiz.demo.repostitories.UserRepository;


@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public ModelAndView viewTaskPage() {
        return new ModelAndView("Issue.html");
    }

    @GetMapping("Register")
   public ModelAndView addUser() {
    ModelAndView mav = new ModelAndView("Register.html");
    User newUser = new User();
    mav.addObject("user", newUser);
    return mav;
}

   @PostMapping("Register")
   public String saveUser(@ModelAttribute User user) {

    User existingUser = this.userRepository.findByUsername(user.getUsername());
    if (existingUser != null) {
        return "<script>alert('Username Already Exists'); window.location.href='/User/Register';</script>";    
    }

    String encodedPassword = BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(12));
    user.setPassword(encodedPassword);
    this.userRepository.save(user);
    return "<script>alert('Sucessfully'); window.location.href='/User/Login';</script>";    
    
}

@GetMapping("Login")
public ModelAndView Login() {
    ModelAndView mav = new ModelAndView("Login");
    User newUser = new User();
    mav.addObject("user", newUser);
    return mav;
}

@PostMapping("Login")
public String LoginProcess(@RequestParam("username") String username, @RequestParam("password") String password) {
    User dbUser = this.userRepository.findByUsername(username);
    if (dbUser == null) {
        return "<script>alert('Invaild Username or Password'); window.location.href='/User/Login';</script>";
    }
    Boolean isPasswordMatched = BCrypt.checkpw(password, dbUser.getPassword());
    if (isPasswordMatched)
        return "Welcome " + dbUser.getName();
    else
    return "<script>alert('Invaild Username or Password'); window.location.href='/User/Login';</script>";
}
}
