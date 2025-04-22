package com.hosManSys.patient.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hosManSys.sequencegenerator.PrefixSequenceGenerator;

@Entity
public class Diagnosys {
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE,generator = "diag_seq")
    @GenericGenerator(name="diag_seq",
            strategy = "com.hosManSys.sequencegenerator.PrefixSequenceGenerator",
            parameters = {
                    @Parameter(name=PrefixSequenceGenerator.INCREMENT_PARAM,value="1"),
                    @Parameter(name=PrefixSequenceGenerator.VALUE_PREFIX_PARAMETER,value="DIG"),
                    @Parameter(name=PrefixSequenceGenerator.NUMBER_FORMAT_PARAMETER,value="%04d")
            }
            )
    private String diag_id;
    @ManyToOne
    @JoinColumn(name = "treatment_id")
    @JsonBackReference(value = "treat_diag")
    private Treatment treatment;
    @NotBlank(message = "Symptoms Cannot be Blank")
    private String symptoms;
    @NotBlank(message = "Duration Cannot be Blank")
    private String duration;
    private String cause;
    private String note;
    
    
    public String getDiag_id() {
        return diag_id;
    }
    public void setDiag_id(String diag_id) {
        this.diag_id = diag_id;
    }
    public Treatment getTreatment() {
        return treatment;
    }
    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }
    public String getSymptoms() {
        return symptoms;
    }
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getCause() {
        return cause;
    }
    public void setCause(String cause) {
        this.cause = cause;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}