package com.hosManSys.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosManSys.admin.dao.AdminDao;
import com.hosManSys.admin.model.Admin;
import com.hosManSys.doctor.dao.DoctorAppointmentDao;
import com.hosManSys.doctor.dao.DoctorDao;
import com.hosManSys.doctor.model.Doctor;
import com.hosManSys.doctor.model.DoctorAppointment;
import com.hosManSys.login.model.Login;
import com.hosManSys.patient.dao.PatientAppointmentDao;
import com.hosManSys.patient.dao.PatientDao;
import com.hosManSys.patient.model.Patient;
import com.hosManSys.patient.model.PatientAppointment;

@Service
public class AdminService {
    
    @Autowired private AdminDao adminDao;
    
    @Autowired private PatientAppointmentDao patientAppDao;
    
    @Autowired private DoctorAppointmentDao doctorAppDao;
    
    @Autowired private DoctorDao doctorDao;
    
    @Autowired private PatientDao patientDao;
    
    public Admin getProfile(Login login) {
        return adminDao.findByAdminId(login.getLogin_id());
    }
    
    public Admin createProfile(Admin admin, Login login) {
        admin.setAdmin_id(login.getLogin_id());
        return adminDao.save(admin);
    }
    
    public List<DoctorAppointment> getAppointmentByStatus(String status){
        return doctorAppDao.getAppointmentsByStatus(status);
    }
    
    public List<PatientAppointment> getPatAppByDocAppId(String app_id){
        return patientAppDao.getPatAppByDocAppId(app_id);
    }
    
    public boolean cancelAppointment(String app_id) {
        try {
        DoctorAppointment docapp= doctorAppDao.getAppointmentById(app_id);
        docapp.setStatus("expire");
        doctorAppDao.save(docapp);
        return true;
        } catch (Exception e) {
            return false;
        }    
    }
    
    public List<Doctor> getAllDoctors(){
        return doctorDao.findAll();
    }
    
    public List<Patient> searchPatient(String name, String field){
        if(field.equals("fname"))
            return patientDao.findByFnameContainingIgnoreCase(name);
        else if(field.equals("lname"))
            return patientDao.findByLnameContainingIgnoreCase(name);
        else if(field.equals("address"))
            return patientDao.findByAddressContainingIgnoreCase(name);
        return null;
    }
    
    public List<Patient> getAllPatients(){
        return patientDao.findAll();
    }
    
    
}