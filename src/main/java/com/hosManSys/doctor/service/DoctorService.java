package com.hosManSys.doctor.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosManSys.admin.exception.InvalidAppointmentException;
import com.hosManSys.doctor.dao.DoctorAppointmentDao;
import com.hosManSys.doctor.dao.DoctorDao;
import com.hosManSys.doctor.exception.UserDetailsNotFoundException;
import com.hosManSys.doctor.model.Doctor;
import com.hosManSys.doctor.model.DoctorAppointment;
import com.hosManSys.login.model.Login;
import com.hosManSys.patient.dao.DiagnosysDao;
import com.hosManSys.patient.dao.PatientAppointmentDao;
import com.hosManSys.patient.dao.PatientDao;
import com.hosManSys.patient.dao.PrescriptionDao;
import com.hosManSys.patient.dao.TestDao;
import com.hosManSys.patient.dao.TreatmentDao;
import com.hosManSys.patient.model.Diagnosys;
import com.hosManSys.patient.model.Patient;
import com.hosManSys.patient.model.PatientAppointment;
import com.hosManSys.patient.model.Prescription;
import com.hosManSys.patient.model.Test;
import com.hosManSys.patient.model.Treatment;

@Service
public class DoctorService {
    
    @Autowired private DoctorDao doctordao;
    
    @Autowired private PatientDao patientDao;
    
    @Autowired private DoctorAppointmentDao docappdao;
    
    @Autowired private PatientAppointmentDao patappdao;
    
    @Autowired private TreatmentDao treatmentDao;
    
    @Autowired private DiagnosysDao diagnosysdao;
    
    @Autowired private PrescriptionDao prescriptiondao;
    
    @Autowired private TestDao testdao;
    
    public Doctor getProfile(Login login) {
        return doctordao.findByDoc_id(login.getLogin_id());
    }
    
    public Doctor createprofile(Doctor doctor, Login login) {
        if(login == null) {
            throw new NullPointerException();
        }
        doctor.setDoc_id(login.getLogin_id());
        return doctordao.save(doctor);
    }
    
    public DoctorAppointment createappointment(DoctorAppointment docapp, Login login) {
        if(! doctordao.existsById(login.getLogin_id())) {
            throw new UserDetailsNotFoundException("405" ,login.getUserName()+" doctor details not found while creating appointment.");
        }
        Doctor doc = doctordao.getById(login.getLogin_id());
        if(! docappdao.getDoctorAppointmentByDate(doc.getDoc_id(), docapp.getDate()).isEmpty()) {
            throw new InvalidAppointmentException("405","Multipal Appointment on Same Date Are not Allowed!!");
        } else if(docapp.getDate().before(new java.util.Date())) {
            throw new InvalidAppointmentException("405", "Appointment cannot be schedule for previous dates.");
        }
        docapp.setDoctor(doc);
        docapp.setStatus("active");
        return docappdao.save(docapp);
    }
    
    public List<DoctorAppointment> getDoctorAppointments(String doc_id){
        return docappdao.getDoctorAppointments(doc_id);
    }
    
    public DoctorAppointment getAppointment(String app_id) {
        return docappdao.getAppointmentById(app_id);
    }
    
    public List<PatientAppointment> getDayAppointment(Login login, Date date){
        return patappdao.getPatAppByDocApp(login.getLogin_id(), date);
    }
    
    public boolean doctorApproval(String appointment_id ) {
        PatientAppointment patapp = patappdao.getById(appointment_id);
        patapp.setStatus("Doc_Approved");
        patappdao.save(patapp);
        return true;
    }
    
    public Treatment getTreatmentByAppId(String app_id) {
        if(! patappdao.existsById(app_id))
            throw new NullPointerException("Invalid Appointment Id");
        PatientAppointment patapp = patappdao.getById(app_id);
        if(patapp.getTreatment() == null) {
            Treatment treatment = new Treatment();
            treatment.setPatientappointment(patapp);
            treatment.setDate(patapp.getDate());
            treatment.setPatient(patapp.getPatient());
            Treatment trmt = treatmentDao.save(treatment);
            patapp.setTreatment(trmt);
            patappdao.save(patapp);
            return trmt;
        } else {
            return patapp.getTreatment();
        }
    }
    
    public List<Treatment> getTreatmentByPatId(String pat_id) {
        Patient patient = patientDao.findByPat_id(pat_id);
        return patient.getTreatment();
    }
    
    public List<Diagnosys> getDiagnosys(String treatment_id) {
        Treatment treatment = treatmentDao.getById(treatment_id);
        return treatment.getDiagnosys();
    }
    
    public boolean setDiagnosys( Diagnosys diagnosys, String treatment_id) {
        Treatment treatment = treatmentDao.getById(treatment_id);
        diagnosys.setTreatment(treatment);
        diagnosysdao.save(diagnosys);
        return true;
    }
    
    public boolean setPrescription( Prescription prescription, String treatment_id) {
        Treatment treatment = treatmentDao.getById(treatment_id);
        prescription.setTreatment(treatment);
        prescriptiondao.save(prescription);
        return true;
    }
    
    public boolean setTest(Test test, String treatment_id) {
        Treatment treatment = treatmentDao.getById(treatment_id);
        test.setTreatment(treatment);
        testdao.save(test);
        return true;
    }
    
    public boolean endAppointment(String treatment_id) {
        Treatment treatment = treatmentDao.getById(treatment_id);
        PatientAppointment patapp = patappdao.getById(treatment.getPatientappointment().getAppointmentId());
        patapp.setStatus("SUCCESS");
        patappdao.save(patapp);
        return true;
    }
    
    public List<Treatment> getTreatmentRecord(String treatment_id) {
        Patient patient = treatmentDao.getById(treatment_id).getPatient();
        List<Treatment> treatmentList = treatmentDao.findByPatient(patient);
        return treatmentList;
    }
    
    public void checkAppointmentDate() {
        java.util.Date date = new java.util.Date();
        String today= new SimpleDateFormat("yyyy-MM-dd").format(date);
        docappdao.updateExpiredAppointmentStatus(Date.valueOf(today));
    }
    

    
}