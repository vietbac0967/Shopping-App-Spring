package com.fit.se.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @PostMapping("/user")
    public String authenticationAccount(String username, String password, Model model){
        if(username.equals("admin") && password.equals("admin")){
            return "admin/admin-page";
        }else{
            return "client/shopping-page";
        }
    }
}
