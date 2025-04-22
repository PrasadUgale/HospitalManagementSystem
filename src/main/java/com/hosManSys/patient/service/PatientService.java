package com.hosManSys.patient.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosManSys.admin.exception.InvalidAppointmentException;
import com.hosManSys.doctor.dao.DoctorAppointmentDao;
import com.hosManSys.doctor.model.DoctorAppointment;
import com.hosManSys.login.model.Login;
import com.hosManSys.patient.dao.PatientAppointmentDao;
import com.hosManSys.patient.dao.PatientDao;
import com.hosManSys.patient.dao.TreatmentDao;
import com.hosManSys.patient.model.Patient;
import com.hosManSys.patient.model.PatientAppointment;
import com.hosManSys.patient.model.Treatment;

@Service
public class PatientService {
    
    @Autowired
    private PatientDao patientdao;
    
    @Autowired
    private DoctorAppointmentDao docappdao;
    
    @Autowired
    private PatientAppointmentDao patappdao;
    
    @Autowired
    private TreatmentDao treatmentdao;
    
    
    public Patient createprofile(Patient patient, Login login) {
        if(login == null) {
            throw new NullPointerException();
        }
        patient.setPat_id(login.getLogin_id());
        return patientdao.save(patient);
    }
    
    public Patient getprofile(Login login) {
        return patientdao.findByPat_id(login.getLogin_id());
    }
    
    public List<DoctorAppointment> getAvailableAppointments(Login login){
        List<DoctorAppointment> docapplist = docappdao.getAppointmentsByStatus("active");
        docapplist.removeAll(getAppliedPatientAppointments(login.getLogin_id()));
        return docapplist;
    }
    
    public List<DoctorAppointment> getAppliedPatientAppointments(String pat_id){
        List<DoctorAppointment> patdocapplist = new ArrayList<>();
        patappdao.findPatientAppointment(pat_id).forEach(app
         ->patdocapplist.add(app.getDoctor_app())); 
        return patdocapplist;
    }

    public boolean scheduleAppointment(DoctorAppointment docapp, Patient patient) {
        List<PatientAppointment> patapplist = patappdao.getPatAppByDocAppId(docapp.getAppointmentId());
        if(patapplist.size() < docapp.getTotal_app()) {
            PatientAppointment patapp = new PatientAppointment();
            patapp.setDoctor_app(docapp);
            patapp.setDate(docapp.getDate());
            patapp.setDoctor_app(docapp);
            patapp.setPatient(patient);
            patapp.setStatus("Not_Approved");
            patappdao.save(patapp);
            return true;
        } else {
            throw new InvalidAppointmentException("403","Appointment Limit Ended! Application closed for this appointment");
        }
    }
    
    public List<Treatment> getMedicalRecords(String pat_id){
        Patient patient = patientdao.getById(pat_id);
        return treatmentdao.findByPatient(patient);
    }
    
    public DoctorAppointment getAppointment(String app_id) {
        return docappdao.getAppointmentById(app_id);
    }
    
}