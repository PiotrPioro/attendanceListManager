package com.openSource.attendanceListManager.service;

import com.openSource.attendanceListManager.entity.Inspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.*;

public class MyUserDetailsService implements UserDetailsService {

    private InspectorService inspectorService;

    @Autowired
    public void setInspectorService(InspectorService inspectorService) {
        this.inspectorService = inspectorService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Inspector inspector = inspectorService.findByEmail(email);
        if(inspector == null){
            throw new UsernameNotFoundException(email);
        }

        return new User(inspector.getEmail(), inspector.getPassword(), Collections.emptyList());
    }
}
