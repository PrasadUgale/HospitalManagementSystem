package com.hosManSys.doctor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hosManSys.doctor.model.Doctor;

public interface DoctorDao extends JpaRepository<Doctor, String> {
    
    @Query("From Doctor where doc_id=?1")
    public Doctor findByDoc_id(String doc_id);
    
}