package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.repsitories.UserRepo;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }
    
    @RequestMapping(value = "/authenticateUser", method = RequestMethod.POST)
    public String authenticateUser(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult,
            HttpSession session) {
    	
    	UserDetails user=userRepo.findByEmail(userForm.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + userForm.getName()));
    	
        System.out.println("Processing registration");
        
        if(userForm.getPassword().equalsIgnoreCase(user.getPassword())){
        	return "user/profile";
        }	
        
               return "redirect:/login";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handler");
        // sending data to view
        model.addAttribute("name", "Substring Technologies");
        model.addAttribute("youtubeChannel", "Learn Code With hashma");
        model.addAttribute("githubRepo", "https://github.com/learncodewithdurgesh/");
        return "home";
    }
    
    

    // about route

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("isLogin", true);
        System.out.println("About page loading");
        return "about";
    }

    // services

    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("services page loading");
        return "services";
    }

    // contact page

    @GetMapping("/contact")
    public String contact() {
        return new String("contact");
    }

    // this is showing login page
    @GetMapping("/login")
    public String login() {
        return new String("login");
    }

    // registration page
    @GetMapping("/register")
    public String register(Model model) {

        UserForm userForm = new UserForm();
        // default data bhi daal sakte hai
        // userForm.setName("Durgesh");
        // userForm.setAbout("This is about : Write something about yourself");
        model.addAttribute("userForm", userForm);

        return "register";
    }

    // processing register

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult,
            HttpSession session) {
        System.out.println("Processing registration");
        // fetch form data
        // UserForm
        System.out.println(userForm);

        // validate form data
        if (rBindingResult.hasErrors()) {
            return "register";
        }

        

        // save to database

        // userservice

        // UserForm--> User
        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic(
        // "https://www.learncodewithdurgesh.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Fdurgesh_sir.35c6cb78.webp&w=1920&q=75")
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
       String encryptedPassword= passwordEncoder.encode(userForm.getPassword());
        
        user.setPassword(encryptedPassword);
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic(
                "https://www.learncodewithdurgesh.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Fdurgesh_sir.35c6cb78.webp&w=1920&q=75");
        userService.saveUser(user);
        System.out.println("user saved :");

        // message = "Registration Successful"

        // add the message:

        Message message = Message.builder().content("Registration Successfull").type(MessageType.green).build();

        session.setAttribute("message", message);

        // redirect login page
        return "redirect:/register";
    }

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	

}











