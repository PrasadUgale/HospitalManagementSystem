package com.hosManSys.patient.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hosManSys.patient.model.Prescription;

public interface PrescriptionDao extends JpaRepository<Prescription, String> {

}