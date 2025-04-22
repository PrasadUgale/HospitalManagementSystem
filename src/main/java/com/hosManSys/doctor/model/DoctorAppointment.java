package com.hosManSys.doctor.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hosManSys.patient.model.PatientAppointment;
import com.hosManSys.sequencegenerator.PrefixSequenceGenerator;

@Entity
public class DoctorAppointment {
    
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE,generator = "app_seq")
    @GenericGenerator(name="app_seq",
            strategy = "com.hosManSys.sequencegenerator.PrefixSequenceGenerator",
            parameters = {
                    @Parameter(name=PrefixSequenceGenerator.INCREMENT_PARAM,value="1"),
                    @Parameter(name=PrefixSequenceGenerator.VALUE_PREFIX_PARAMETER,value="APP_"),
                    @Parameter(name=PrefixSequenceGenerator.NUMBER_FORMAT_PARAMETER,value="%05d")
            }
            )
    private String appointmentId;
    
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonBackReference
    private Doctor doctor;
    
    @OneToMany(mappedBy = "doctor_app")
    @JsonManagedReference(value = "docApp_patApp")
    private List<PatientAppointment> patient_app;
    
    private String status;

    private int total_app;
    
    private Date date;
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<PatientAppointment> getPatient_app() {
        return patient_app;
    }

    public void setPatient_app(List<PatientAppointment> patient_app) {
        this.patient_app = patient_app;
    }

    public int getTotal_app() {
        return total_app;
    }

    public void setTotal_app(int total_app) {
        this.total_app = total_app;
    }

    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}