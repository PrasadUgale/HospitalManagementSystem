package com.hosManSys.patient.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hosManSys.doctor.model.DoctorAppointment;
import com.hosManSys.sequencegenerator.PrefixSequenceGenerator;

@Entity
public class PatientAppointment {
    
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE,generator = "pat_seq")
    @GenericGenerator(name="pat_seq",
            strategy = "com.hosManSys.sequencegenerator.PrefixSequenceGenerator",
            parameters = {
                    @Parameter(name=PrefixSequenceGenerator.INCREMENT_PARAM,value="1"),
                    @Parameter(name=PrefixSequenceGenerator.VALUE_PREFIX_PARAMETER,value="PAT_"),
                    @Parameter(name=PrefixSequenceGenerator.NUMBER_FORMAT_PARAMETER,value="%05d")
            }
            )
    private String appointmentId;
    
    @ManyToOne
    @JoinColumn(name = "doctor_appointment")
    @JsonBackReference(value = "docApp_patApp")
    private DoctorAppointment doctor_app;
    
    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonBackReference(value = "patiet_appointment")
    private Patient patient;
    
    private Date date;
    
    private String status;
    
    @OneToOne
    @JoinColumn(name = "treatment")
    @JsonManagedReference(value = "patApp_treat")
    private Treatment treatment;
    

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public DoctorAppointment getDoctor_app() {
        return doctor_app;
    }

    public void setDoctor_app(DoctorAppointment doctor_app) {
        this.doctor_app = doctor_app;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}