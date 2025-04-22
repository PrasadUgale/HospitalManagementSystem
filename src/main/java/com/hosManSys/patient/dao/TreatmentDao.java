package com.hosManSys.patient.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hosManSys.patient.model.Patient;
import com.hosManSys.patient.model.Treatment;

public interface TreatmentDao extends JpaRepository<Treatment, String> {
    
    public List<Treatment> findByPatient(Patient patient); 
}