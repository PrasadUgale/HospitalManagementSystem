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
public class Test {
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE,generator = "tst_seq")
    @GenericGenerator(name="tst_seq",
            strategy = "com.hosManSys.sequencegenerator.PrefixSequenceGenerator",
            parameters = {
                    @Parameter(name=PrefixSequenceGenerator.INCREMENT_PARAM,value="1"),
                    @Parameter(name=PrefixSequenceGenerator.VALUE_PREFIX_PARAMETER,value="TST"),
                    @Parameter(name=PrefixSequenceGenerator.NUMBER_FORMAT_PARAMETER,value="%04d")
            }
            )
    private String test_id;
    @ManyToOne
    @JoinColumn(name = "treatment_id")
    @JsonBackReference(value = "treat_test")
    private Treatment treatment;
    @NotBlank(message = "Test Name is Not Given")
    private String testName;
    @NotBlank(message = "Test Result is Not Given")
    private String testResult;
    private String testRemark;
    
    
    public String getTest_id() {
        return test_id;
    }
    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }
    public Treatment getTreatment() {
        return treatment;
    }
    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }
    public String getTestName() {
        return testName;
    }
    public void setTestName(String testName) {
        this.testName = testName;
    }
    public String getTestResult() {
        return testResult;
    }
    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }
    public String getTestRemark() {
        return testRemark;
    }
    public void setTestRemark(String testRemark) {
        this.testRemark = testRemark;
    }
    
}