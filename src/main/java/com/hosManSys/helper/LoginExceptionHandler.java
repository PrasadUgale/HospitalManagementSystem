package com.hosManSys.helper;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hosManSys.admin.exception.InvalidAppointmentException;
import com.hosManSys.doctor.exception.UserDetailsNotFoundException;
import com.hosManSys.login.exception.InvalidPrivilegeException;
import com.hosManSys.login.exception.UserNameExistException;
import com.hosManSys.login.exception.UserNotLoggedInException;
import com.hosManSys.login.model.Login;

import ch.qos.logback.classic.Logger;


@RestControllerAdvice
public class LoginExceptionHandler {
    
    Logger logger = (Logger) LoggerFactory.getLogger(LoginExceptionHandler.class);
    
    
    @ExceptionHandler(UserNameExistException.class)
    public ResponseEntity<Object> handleUserNameExistException(UserNameExistException userNameExist, Model md, HttpSession session){
        Login login = (Login) session.getAttribute("user");
        logger.warn("Duplicate UserName entry given by user");
        return new ResponseEntity<Object>("UserName Already Exist. Use different UserName", HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity<Object> handleUserNotLoggedInException(UserNotLoggedInException usernotloggedin, Model md){
        logger.warn("Trying to Access Resource without logIn");
        return new ResponseEntity<Object>("You are not logged in.", HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(InvalidPrivilegeException.class)
    public ResponseEntity<Object> handleInvalidPrivilegeException(HttpSession session, InvalidPrivilegeException invalidprivilege, Model md){
        Login login = (Login) session.getAttribute("user");
        logger.warn(login.getUserName() +" Trying to access Restricted resources");
        return new ResponseEntity<Object>("You are Not allowed to use this resource.", HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(InvalidAppointmentException.class)
    public ResponseEntity<Object> handleInvalidAppointmentException(InvalidAppointmentException invalidappointment, Model md, HttpSession session) {
        logger.warn(invalidappointment.getErrorCode()+": "+invalidappointment.getErrorMessage());
        return new ResponseEntity<Object>(invalidappointment.getErrorCode()+": "+invalidappointment.getErrorMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(UserDetailsNotFoundException.class)
    public ResponseEntity<Object> handleUserDetailsNotFoundException(UserDetailsNotFoundException userdetailsnotfound, Model md, HttpSession session) {
        logger.warn(userdetailsnotfound.getErrorCode()+" : "+userdetailsnotfound.getErrorMessage());
        return new ResponseEntity<Object>(userdetailsnotfound.getErrorCode()+" : "+userdetailsnotfound.getErrorMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException nullpointer, Model md) {
        logger.error("Null Pointer error occured");
        return new ResponseEntity<Object>("Null Pointer Exception.", HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException entityNotFound, Model md) {
        logger.error("Entity not Found Exception");
        return new ResponseEntity<Object>("Entity Not Found", HttpStatus.BAD_REQUEST);
    }
    
    
}