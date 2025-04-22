package com.hosManSys.login.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import com.hosManSys.sequencegenerator.PrefixSequenceGenerator;

@Entity
@Table(name = "Login")
public class Login {
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE,generator = "hms_seq")
    @GenericGenerator(name="hms_seq",
            strategy = "com.hosManSys.sequencegenerator.PrefixSequenceGenerator",
            parameters = {
                    @Parameter(name=PrefixSequenceGenerator.INCREMENT_PARAM,value="1"),
                    @Parameter(name=PrefixSequenceGenerator.VALUE_PREFIX_PARAMETER,value="HMS"),
                    @Parameter(name=PrefixSequenceGenerator.NUMBER_FORMAT_PARAMETER,value="%04d")
            }
            )
    private String login_id;
    
    @Column(unique = true, nullable = false)
    @NotNull
    private String userName;
    
    @Length(min = 6, message = "Password length shoud be between 6 to 20")
    @NotNull
    private String password;

    private String access;
    
    private boolean activate;

    public String getLogin_id() {
        return login_id;
    }
    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAccess() {
        return access;
    }
    public void setAccess(String access) {
        this.access = access;
    }
    public boolean isActivate() {
        return activate;
    }
    public void setActivate(boolean activate) {
        this.activate = activate;
    }
    
    
}