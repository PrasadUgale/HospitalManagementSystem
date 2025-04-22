package com.hosManSys.doctor.controller;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hosManSys.admin.controller.AdminController;
import com.hosManSys.doctor.model.Doctor;
import com.hosManSys.doctor.model.DoctorAppointment;
import com.hosManSys.doctor.service.DoctorService;
import com.hosManSys.login.model.Login;
import com.hosManSys.login.service.LoginService;
import com.hosManSys.patient.model.Diagnosys;
import com.hosManSys.patient.model.Prescription;
import com.hosManSys.patient.model.Test;
import com.hosManSys.patient.model.Treatment;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    
    @Autowired private DoctorService doctorService;
    @Autowired private LoginService loginService;
    
    Logger logger = (Logger) LoggerFactory.getLogger(AdminController.class);
    
    
    @GetMapping("profile")
    public ResponseEntity<Object> getprofile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) auth.getPrincipal();
        Login login = loginService.getLoginByUsername(principal.getUsername());
        Doctor doctor = doctorService.getProfile(login);
        if(doctor == null)
            return new ResponseEntity<Object>("Profile Data Not Found.", HttpStatus.NOT_FOUND);
        return new ResponseEntity<Object>(doctor, HttpStatus.FOUND);
    }
    
    @PostMapping("profile")
    public ResponseEntity<Object> setprofile(@Valid @RequestBody Doctor doctor, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity<Object>("Validation failure: "+ errors, HttpStatus.BAD_REQUEST);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) auth.getPrincipal();
        Login login = loginService.getLoginByUsername(principal.getUsername());
        Doctor newprofile = doctorService.createprofile(doctor, login);
        return new ResponseEntity<Object>("Profile Data Updated Success", HttpStatus.OK);
    }
    
    @GetMapping(path = "create_app")
    public ResponseEntity<Object> getCreateApp(HttpSession session) {
        loginService.securityCheck(session, "doctor");
        doctorService.checkAppointmentDate();
        Login user = (Login) session.getAttribute("user");
        return new ResponseEntity<Object>(doctorService.getDoctorAppointments(user.getLogin_id()), HttpStatus.FOUND);
    }
    
    @PostMapping("create_app")
    public ResponseEntity<Object> setCreateApp(@Valid @RequestBody DoctorAppointment doc_appointment,BindingResult bindingResult, HttpSession session) {
        if(bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity<Object>("Validation failure: "+ errors, HttpStatus.BAD_REQUEST);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) auth.getPrincipal();
        Login login = loginService.getLoginByUsername(principal.getUsername());
        DoctorAppointment createdApp = doctorService.createappointment(doc_appointment, login);
        logger.info(login.getUserName()+" has created appointment for "+ createdApp.getDate());
        return new ResponseEntity<Object>("Appointment Created Successfully.", HttpStatus.OK);
    }
    @GetMapping("appointmentByDate")
    public ResponseEntity<Object> todayapp(@RequestParam("date") String date) { 
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       User principal = (User) auth.getPrincipal();
       Login login = loginService.getLoginByUsername(principal.getUsername());
       return new ResponseEntity<Object>(doctorService.getDayAppointment(login, Date.valueOf(date)), HttpStatus.OK);
    }
   
     @GetMapping("treatment/start")
     public ResponseEntity<Object> startAppointment(@RequestParam("app_id") String app_id) {
         Treatment treatment = doctorService.getTreatmentByAppId(app_id);
         logger.info("Appointment Started: "+ app_id);
         return new ResponseEntity<Object>(treatment, HttpStatus.OK);
     }
   
       
     @GetMapping("patient_history")
     public ResponseEntity<Object> getPatientInfo(@RequestParam("pat_id") String pat_id) {
         List<Treatment> trt = doctorService.getTreatmentByPatId(pat_id);
         return new ResponseEntity<Object>(trt, HttpStatus.OK);
     }      
     
     @PostMapping("treatment/diagnosys/{treatment_id}")
     public ResponseEntity<Object> setDiagnosys(@Valid @RequestBody Diagnosys diagnosys ,BindingResult bindingResult ,@PathVariable("treatment_id") String treatment_id) {
         if(bindingResult.hasErrors()) {
             List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
               return new ResponseEntity<Object>("Validation failure: "+ errors, HttpStatus.BAD_REQUEST);
           }
         doctorService.setDiagnosys(diagnosys, treatment_id);
         return new ResponseEntity<Object>("Diagnosys Data Inserted Successfully", HttpStatus.OK);
     }
     
     @PostMapping("treatment/test/{treatment_id}")
     public ResponseEntity<Object> setTest(@Valid @RequestBody Test test, BindingResult bindingResult,@PathVariable("treatment_id") String treatment_id) {
         if(bindingResult.hasErrors()) {
             List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
               return new ResponseEntity<Object>("Validation failure: "+ errors, HttpStatus.BAD_REQUEST);
           }
         doctorService.setTest(test, treatment_id);
         return new ResponseEntity<Object>("Test Data Inserted Successfully", HttpStatus.OK);
     }
     
     @PostMapping("treatment/prescription/{treatment_id}")
     public ResponseEntity<Object> setPrescription(@Valid @RequestBody Prescription prescription, BindingResult bindingResult,@PathVariable("treatment_id") String treatment_id) {
         if(bindingResult.hasErrors()) {
             List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
               return new ResponseEntity<Object>("Validation failure: "+ errors, HttpStatus.BAD_REQUEST);
            }
         doctorService.setPrescription(prescription, treatment_id);
         return new ResponseEntity<Object>("Prescription Data Inserted Successfully", HttpStatus.OK);
     }
     
     
     @GetMapping("treatment/end/{treatment_id}")
     public ResponseEntity<Object> endAppointment(@PathVariable String treatment_id, HttpSession session) {
         doctorService.endAppointment(treatment_id);
         logger.info("Appointment Ended ");
         return new ResponseEntity<Object>("Appointment Ended", HttpStatus.OK);
     }
     
   
    @GetMapping("showAppApplication")
    public ResponseEntity<Object> getAppointemnts(@RequestParam("date") String date, HttpSession session) {
       return new ResponseEntity<Object>( doctorService.getDayAppointment((Login) session.getAttribute("user"),Date.valueOf(date)), HttpStatus.OK);
    }
    
    
    @GetMapping("approveApplication")
    public ResponseEntity<Object> approveAppointment(@RequestParam("appointmentId") String appointment_id, HttpSession session) {
       doctorService.doctorApproval(appointment_id);
       return new ResponseEntity<Object>("Appointment is Approved", HttpStatus.OK);
    }

    
}