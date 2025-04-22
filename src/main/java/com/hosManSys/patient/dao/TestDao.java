package com.hosManSys.patient.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hosManSys.patient.model.Test;

public interface TestDao extends JpaRepository<Test, String> {

}