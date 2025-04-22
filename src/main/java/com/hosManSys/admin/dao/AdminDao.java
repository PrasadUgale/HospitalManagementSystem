package com.hosManSys.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hosManSys.admin.model.Admin;

public interface AdminDao extends JpaRepository<Admin, String> {
    
    @Query("From Admin where admin_id=?1")
    public Admin findByAdminId(String admin_id);
    
}