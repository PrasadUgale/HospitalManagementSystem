package com.hosManSys.login.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hosManSys.login.model.Login;

public interface LoginDao extends JpaRepository<Login, String> {
    
    @Query("FROM Login WHERE userName= :user and password= :pass")
    public Login validateLogin(String user, String pass);
    
    public Login findByUserName(String userName);
}