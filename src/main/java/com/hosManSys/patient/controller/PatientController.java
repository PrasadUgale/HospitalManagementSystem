package com.hosManSys.patient.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.aspectj.apache.bcel.generic.BranchHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.hosManSys.login.model.Login;
import com.hosManSys.login.service.LoginService;
import com.hosManSys.patient.model.Patient;
import com.hosManSys.patient.model.Treatment;
import com.hosManSys.patient.service.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {
    
    @Autowired private PatientService patientService;
    @Autowired private LoginService loginService;
    
    
    @GetMapping("profile")
    public ResponseEntity<Patient> getprofile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) auth.getPrincipal();
        Login login = loginService.getLoginByUsername(principal.getUsername());
        Patient patient = patientService.getprofile(login);
        if(patient == null)
            return new ResponseEntity<Patient>(patient, HttpStatus.NOT_FOUND);
       // patient.setPat_id(null);
        //patient.setPat_appointment(null);
        patient.getPat_id();
        patient.getPat_appointment();
        return new ResponseEntity<Patient>(patient, HttpStatus.FOUND);
    }
    
    @PostMapping(path = "profile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> setprofile(@Valid @RequestBody Patient patient, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity<Object>("Validation failure: "+ errors, HttpStatus.BAD_REQUEST);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) auth.getPrincipal();
        Login login = loginService.getLoginByUsername(principal.getUsername());
        Patient newprofile = patientService.createprofile(patient, login);
        return new ResponseEntity<Object>("Profile Data Update Success.", HttpStatus.OK);
    }
    
    @GetMapping("appliedApp")
    public ResponseEntity<Object> getAppointments(HttpSession session) {
        Login login = (Login)session.getAttribute("user");
        return new ResponseEntity<Object>(patientService.getAvailableAppointments(login), HttpStatus.FOUND);
    }
    
    @GetMapping("expiredApp")
    public ResponseEntity<Object> getExpiredAppointments(HttpSession session) {
        Login login = (Login)session.getAttribute("user");
        return new ResponseEntity<Object>(patientService.getAppliedPatientAppointments(login.getLogin_id()), HttpStatus.FOUND);
    }
    
    @GetMapping("schedule")
    public ResponseEntity<Object> scheduleAppointment(@RequestParam("appointment_id") String appointment_id, HttpSession session) {
        patientService.scheduleAppointment(patientService.getAppointment(appointment_id), 
                patientService.getprofile(((Login)session.getAttribute("user"))));
        return new ResponseEntity<Object>("Appoint Created Successfully", HttpStatus.ACCEPTED);
    }
    
    @GetMapping("medical_history")
    public ResponseEntity<Object> getMedicalRecords(HttpSession session) {
        List<Treatment> treatments = patientService.getMedicalRecords(((Login)session.getAttribute("user")).getLogin_id());
        return new ResponseEntity<Object>(treatments, HttpStatus.FOUND);
    }
    
}