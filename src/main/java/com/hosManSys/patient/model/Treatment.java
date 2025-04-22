package com.hosManSys.patient.model;

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
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hosManSys.sequencegenerator.PrefixSequenceGenerator;

@Entity
public class Treatment {
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE,generator = "trmt_seq")
    @GenericGenerator(name="trmt_seq",
            strategy = "com.hosManSys.sequencegenerator.PrefixSequenceGenerator",
            parameters = {
                    @Parameter(name=PrefixSequenceGenerator.INCREMENT_PARAM,value="1"),
                    @Parameter(name=PrefixSequenceGenerator.VALUE_PREFIX_PARAMETER,value="TMT"),
                    @Parameter(name=PrefixSequenceGenerator.NUMBER_FORMAT_PARAMETER,value="%04d")
            }
            )
    private String treatment_id;
    private Date date;
    @OneToOne
    @JoinColumn(name = "patient_appointment")
    @JsonBackReference(value = "patApp_treat")
    private PatientAppointment patientappointment;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonBackReference(value = "pat_treat")
    private Patient patient;
    @OneToMany(mappedBy = "treatment")
    @JsonManagedReference(value = "treat_pres")
    private List<Prescription> prescription;
    @OneToMany(mappedBy = "treatment")
    @JsonManagedReference(value = "treat_diag")
    private List<Diagnosys> diagnosys;
    @OneToMany(mappedBy = "treatment")
    @JsonManagedReference(value = "treat_test")
    private List<Test> test;
    private String suggestion;

    
    public String getTreatment_id() {
        return treatment_id;
    }

    public void setTreatment_id(String treatment_id) {
        this.treatment_id = treatment_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PatientAppointment getPatientappointment() {
        return patientappointment;
    }

    public void setPatientappointment(PatientAppointment patientappointment) {
        this.patientappointment = patientappointment;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Prescription> getPrescription() {
        return prescription;
    }

    public void setPrescription(List<Prescription> prescription) {
        this.prescription = prescription;
    }

    public List<Diagnosys> getDiagnosys() {
        return diagnosys;
    }

    public void setDiagnosys(List<Diagnosys> diagnosys) {
        this.diagnosys = diagnosys;
    }

    public List<Test> getTest() {
        return test;
    }

    public void setTest(List<Test> test) {
        this.test = test;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
    
    

}