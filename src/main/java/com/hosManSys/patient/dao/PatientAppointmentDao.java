package com.hosManSys.patient.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hosManSys.patient.model.PatientAppointment;

public interface PatientAppointmentDao extends JpaRepository<PatientAppointment, String>{
    
    @Query("From PatientAppointment where patient_id=?1")
    public List<PatientAppointment> findPatientAppointment(String pat_id);
    
    @Query("From PatientAppointment patapp inner join patapp.doctor_app doc on doc.appointmentId=doctor_appointment AND doc.doctor.doc_id= :doc_id AND doc.date= :date")
    public List<PatientAppointment> getPatAppByDocApp(String doc_id, java.sql.Date date);
    
    @Query("From PatientAppointment patapp inner join patapp.doctor_app doc on doc.appointmentId=doctor_appointment AND doctor_appointment=?1")
    public List<PatientAppointment> getPatAppByDocAppId(String app_id);

    
}