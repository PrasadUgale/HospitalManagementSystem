package com.hosManSys.login.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hosManSys.login.dao.LoginDao;
import com.hosManSys.login.model.Login;
@Service
public class JwtUserDetailsService implements UserDetailsService {
    
    @Autowired LoginDao loginDao;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login login = loginDao.findByUserName(username);
        List<SimpleGrantedAuthority> role = null;
        if (login.getUserName().equals(username)) {
            role = Arrays.asList(new SimpleGrantedAuthority(login.getAccess()));
            return User.withUsername(username).password(login.getPassword())
                    .authorities(login.getAccess())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}