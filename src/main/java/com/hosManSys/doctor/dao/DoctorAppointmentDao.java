package com.hosManSys.doctor.dao;

import java.sql.Date;
import java.util.List;

import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hosManSys.doctor.model.DoctorAppointment;

@Transactional
public interface DoctorAppointmentDao extends JpaRepository<DoctorAppointment, String> {
    
    @Query("From DoctorAppointment where doctor_id= :doct_id and date= :ondate")
    public List<DoctorAppointment> getDoctorAppointmentByDate(@Param("doct_id") String doct_id,@Param("ondate") Date ondate);
    
    @Query("From DoctorAppointment where status=?1")
    public List<DoctorAppointment> getAppointmentsByStatus(String status);
    
    @Query("From DoctorAppointment where appointmentId=?1")
    public DoctorAppointment getAppointmentById(String appointment_id);
    
    @Query("Select patient_app From DoctorAppointment where doctor_id= :doct_id and date= :ondate")
    public List<Object> getPatAppByDocId(@Param("doct_id") String doct_id,@Param("ondate") Date ondate);
     
    @Query("From DoctorAppointment where doctor_id=?1")
    public List<DoctorAppointment> getDoctorAppointments(String doc_id);
     
    @Modifying
    @Query("Update DoctorAppointment da set da.status='expire'"+" where date<:today")
    public void updateExpiredAppointmentStatus(@Param("today") Date today);
     
}