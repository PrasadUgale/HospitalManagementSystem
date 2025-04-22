package com.hosManSys.patient.model;

import java.sql.Date;

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
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hosManSys.sequencegenerator.PrefixSequenceGenerator;

@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE,generator = "pres_seq")
    @GenericGenerator(name="pres_seq",
            strategy = "com.hosManSys.sequencegenerator.PrefixSequenceGenerator",
            parameters = {
                    @Parameter(name=PrefixSequenceGenerator.INCREMENT_PARAM,value="1"),
                    @Parameter(name=PrefixSequenceGenerator.VALUE_PREFIX_PARAMETER,value="PRES"),
                    @Parameter(name=PrefixSequenceGenerator.NUMBER_FORMAT_PARAMETER,value="%04d")
            }
            )
    private String presc_id;
    @NonNull
    private String app_id;
    @ManyToOne
    @JoinColumn(name = "treatment_id")
    @JsonBackReference(value = "treat_pres")
    private Treatment treatment;
    @NotBlank(message = "Medicine Name is not given.")
    private String medName;
    @NotBlank(message = "Consumption Instruction not given.")
    private String consumption;
    private String suggestion;
    
    public String getPresc_id() {
        return presc_id;
    }
    public void setPresc_id(String presc_id) {
        this.presc_id = presc_id;
    }
    public String getApp_id() {
        return app_id;
    }
    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }
    public Treatment getTreatment() {
        return treatment;
    }
    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }
    public String getMedName() {
        return medName;
    }
    public void setMedName(String medName) {
        this.medName = medName;
    }
    public String getConsumption() {
        return consumption;
    }
    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }
    public String getSuggestion() {
        return suggestion;
    }
    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}