package com.hosManSys.admin.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.hosManSys.admin.validation.Date;

@Entity
public class Admin {
    @Id
    private String admin_id;
    @NotBlank(message = "First Name Cannot be Blank")
    private String fname;
    @NotBlank(message = "Last Name Cannot be Blank")
    private String lname;
    @Date
    private String birthDate;
    @NotBlank(message = "Gender Cannot be Blank")
    private String gender;
    @Length(max = 12, min = 10, message = "Invalid Phone Number")
    private String phone;
    @NotBlank(message = "Address Cannot be Blank")
    private String address;
    
    public String getAdmin_id() {
        return admin_id;
    }
    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }
    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public String getLname() {
        return lname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    
    
}