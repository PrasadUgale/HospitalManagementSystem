package com.hosManSys.patient.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hosManSys.admin.validation.Date;

@Entity
@Table(name = "Patient")
public class Patient {
    @Id
    private String pat_id;
    @NotBlank(message = "First Name Cannot be Blank")
    private String fname;
    @NotBlank(message = "Last Name Cannot be Blank")
    private String lname;
    @Date
    private String dob;
    @NotBlank(message = "Gender Cannot be Blank")
    private String gender;
    @Length(max = 12, min = 10, message = "Invalid Phone Number")
    private String phone;
    @NotBlank(message = "Address Cannot be Blank")
    private String address;
    @OneToMany(mappedBy = "patient")
    @JsonManagedReference(value = "patiet_appointment")
    private List<PatientAppointment> pat_appointment;
    @OneToMany(mappedBy = "patient")
    @JsonManagedReference(value = "pat_treat")
    private List<Treatment> treatment;
    
    
    public List<Treatment> getTreatment() {
        return treatment;
    }
    public void setTreatment(List<Treatment> treatment) {
        this.treatment = treatment;
    }
    public List<PatientAppointment> getPat_appointment() {
        return pat_appointment;
    }
    public void setPat_appointment(List<PatientAppointment> pat_appointment) {
        this.pat_appointment = pat_appointment;
    }
    public String getPat_id() {
        return pat_id;
    }
    public void setPat_id(String pat_id) {
        this.pat_id = pat_id;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
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
    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
     
     

}