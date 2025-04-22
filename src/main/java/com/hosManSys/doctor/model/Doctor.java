package com.hosManSys.doctor.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hosManSys.admin.validation.Date;

@Entity
@Table(name = "Doctor")
public class Doctor {
    @Id
    private String doc_id;
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
    @NotBlank(message = "Qualification Missing")
    private String qualification;
    @NotBlank(message = "Specialization Missing")
    private String specialization;
    @OneToMany(mappedBy = "doctor")
    @JsonManagedReference
    private List<DoctorAppointment> doctorappointment;
    
    public List<DoctorAppointment> getDoctorappointment() {
        return doctorappointment;
    }
    public void setDoctorappointment(List<DoctorAppointment> doctorappointment) {
        this.doctorappointment = doctorappointment;
    }
    public String getDoc_id() {
        return doc_id;
    }
    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
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
    public String getQualification() {
        return qualification;
    }
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    
    
}