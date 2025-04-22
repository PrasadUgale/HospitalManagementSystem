package com.hosManSys.patient.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hosManSys.login.model.Login;
import com.hosManSys.patient.model.Patient;

public interface PatientDao extends JpaRepository<Patient, String>{
    
    @Query("From Patient where pat_id=?1")
    public Patient findByPat_id(String pat_id);
    
    @Query("From Patient where login=?1")
    public Patient findByLogin(Login login);
    
    public List<Patient> findByFnameContainingIgnoreCase(String name);
    public List<Patient> findByLnameContainingIgnoreCase(String name);
    public List<Patient> findByAddressContainingIgnoreCase(String name);

    
    
}