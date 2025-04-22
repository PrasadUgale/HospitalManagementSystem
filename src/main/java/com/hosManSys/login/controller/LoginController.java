package com.hosManSys.login.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hosManSys.admin.controller.AdminController;
import com.hosManSys.login.model.Login;
import com.hosManSys.login.service.LoginService;

import ch.qos.logback.classic.Logger;

@RestController
public class LoginController {
    
    @Autowired private LoginService loginservice;
    Logger logger = (Logger) LoggerFactory.getLogger(AdminController.class);
    
    @PostMapping("register")
    public ResponseEntity<Object> newRegister(@Valid @RequestBody Login login, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity<Object>("Validation failure: "+ errors, HttpStatus.BAD_REQUEST);
        }
        loginservice.registerPatient(login);
        logger.info("New account created "+ login.getUserName());
        return new ResponseEntity<Object>("Account Created Successfully", HttpStatus.CREATED);
    }
    
    /*
     * @PostMapping("login") public ResponseEntity<Object>
     * validatelogin(@RequestBody Login login, HttpSession session) { Login
     * validatedlogin = loginservice.login(login); if(validatedlogin == null) {
     * return new ResponseEntity<Object>("Login Unsuccessfull.",
     * HttpStatus.NOT_FOUND); } session.setAttribute("loggedIn", true);
     * session.setAttribute("user", validatedlogin);
     * logger.trace(validatedlogin.getUserName()+ " Logged In"); return new
     * ResponseEntity<Object>("Login success!!", HttpStatus.OK); }
     */
    
    @GetMapping("logout")
    public ResponseEntity<Object> logout(HttpSession session) {
        logger.trace(((Login)session.getAttribute("user")).getUserName()+" Logged Out");
        session.removeAttribute("user");
        session.removeAttribute("loggedIn");
        return new ResponseEntity<Object>("User Logged Out", HttpStatus.OK);
    }
    
}