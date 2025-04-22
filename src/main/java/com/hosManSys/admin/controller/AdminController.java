package com.hosManSys.admin.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.hosManSys.admin.model.Admin;
import com.hosManSys.admin.service.AdminService;
import com.hosManSys.doctor.model.Doctor;
import com.hosManSys.doctor.model.DoctorAppointment;
import com.hosManSys.login.model.Login;
import com.hosManSys.login.service.LoginService;
import com.hosManSys.patient.model.Patient;
import ch.qos.logback.classic.Logger;


@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired private AdminService adminService;
    @Autowired private LoginService loginService;
    
    Logger logger = (Logger) LoggerFactory.getLogger(AdminController.class);
    
    
    @GetMapping("profile")
    public ResponseEntity<Object> getprofile( ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) auth.getPrincipal();
        Login login = loginService.getLoginByUsername(principal.getUsername());
        Admin admin = adminService.getProfile(login);
        if(admin == null) {
            return new ResponseEntity<Object>("No Profile Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(admin, HttpStatus.OK);
    }
    
    @PostMapping("profile")
    public ResponseEntity<Object> setprofile(@Valid @RequestBody Admin admin, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity<Object>("Validation failure: "+ errors, HttpStatus.BAD_REQUEST);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) auth.getPrincipal();
        Login login = loginService.getLoginByUsername(principal.getUsername());
        Admin newprofile = adminService.createProfile(admin, login);
        return new ResponseEntity<Object>("Data Inserted Successfully", HttpStatus.CREATED);
    }
    
    
    @GetMapping("allDoctors")
    public ResponseEntity<List<Doctor>> getDoctors() {
        List<Doctor> doctor_list = adminService.getAllDoctors();
        doctor_list.forEach(a->a.setDoctorappointment(null));
        return new ResponseEntity<List<Doctor>>(doctor_list, HttpStatus.OK);
    }
    
    @PostMapping("regDoctor")
    public ResponseEntity<Object> registerDoctor(@Valid @RequestBody Login login, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) { 
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity<Object>("Validation failure: "+ errors, HttpStatus.BAD_REQUEST);
        }
        loginService.registerDoctor(login);
        logger.info("New Doctor Registered by admin");
        return new ResponseEntity<Object>("Doctor is Registered.", HttpStatus.CREATED);
    }
    
    @GetMapping("activeAppointment")
    public ResponseEntity<List<DoctorAppointment>> getActiveAppointments() {
        return new ResponseEntity<List<DoctorAppointment>>(adminService.getAppointmentByStatus("active"), HttpStatus.OK);
    }
    
    @GetMapping("expireAppointment")
    public ResponseEntity<List<DoctorAppointment>> getExpiredAppointments() {
        return new ResponseEntity<List<DoctorAppointment>>(adminService.getAppointmentByStatus("expire"), HttpStatus.OK);
    }
    
    @GetMapping("showAppInfo")
    public ResponseEntity<Object> getAppointmentInfo(@RequestParam("app_id") String app_id) {
        logger.trace("Admin checked information of " + app_id);
        return new ResponseEntity<Object>(adminService.getPatAppByDocAppId(app_id), HttpStatus.OK);
    }
    
    @GetMapping("cancelApp")
    public ResponseEntity<String> cancelAppointment(@RequestParam("app_id") String app_id) {
        adminService.cancelAppointment(app_id);
        logger.info("Appointment with id "+ app_id+" Cancelled By admin");
        return new ResponseEntity<String>("Appointmen is cancelled.", HttpStatus.OK);
    }
    
    
    @GetMapping("searchPatient")
    public ResponseEntity<Object> searchPatient(@RequestParam("name") String name,@RequestParam("field") String field) {
        List<Patient> patientList = adminService.searchPatient(name, field);
        if(patientList.isEmpty()) {
            return new ResponseEntity<Object>("Not Record Found for given name.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(patientList, HttpStatus.OK);
    }
    
    @GetMapping("getAllPatient")
    public ResponseEntity<Object> searchPatient() {
        List<Patient> patientList = adminService.getAllPatients();
        if(patientList.isEmpty()) {
            return new ResponseEntity<Object>("Not Record Found for given name.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(patientList, HttpStatus.OK);
    }
    
}