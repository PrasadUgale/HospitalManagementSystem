package com.hosManSys.login.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.hosManSys.login.dao.LoginDao;
import com.hosManSys.login.exception.InvalidPrivilegeException;
import com.hosManSys.login.exception.UserNameExistException;
import com.hosManSys.login.exception.UserNotLoggedInException;
import com.hosManSys.login.model.Login;

@Service
public class LoginService {
    
    @Autowired
    private LoginDao logindao;
    
    public Login registerPatient(Login login) {
        Login createdLogin = new Login();
        login.setAccess("patient");
        login.setActivate(false);
        try {
            createdLogin = logindao.save(login);
        } catch (DataIntegrityViolationException e) {
            throw new UserNameExistException();
        }
        return createdLogin;
    }
    
    public Login registerDoctor(Login login) {
        Login createdLogin = new Login();
        login.setAccess("doctor");
        login.setActivate(true);
        try {
            createdLogin = logindao.save(login);
        } catch (DataIntegrityViolationException e) {
            throw new UserNameExistException();
        }
        return createdLogin;
    }
    
    public Login login(Login login) {
        return logindao.validateLogin(login.getUserName(), login.getPassword());
    }
    
    public boolean securityCheck(HttpSession session, String acccess) {
        if(session.getAttribute("loggedIn") != null) {
            if((boolean)session.getAttribute("loggedIn")) {
                Login login = (Login)session.getAttribute("user");
                if(login.getAccess().equals(acccess)) {
                    return true;
                }
                else {
                    throw new InvalidPrivilegeException("403","You Are Not Allowed to access this Resource!");
                }
            }else {
                throw new UserNotLoggedInException();
            }
        }
        throw new UserNotLoggedInException();
    }
    
    public Login getLoginByUsername(String username) {
        return logindao.findByUserName(username);
    }
    
}