package com.hosManSys.patient.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hosManSys.patient.model.Diagnosys;

public interface DiagnosysDao extends JpaRepository<Diagnosys, String> {

}