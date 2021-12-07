package com.openSource.attendanceListManager.service;

import com.openSource.attendanceListManager.entity.Inspector;
import com.openSource.attendanceListManager.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

        //List<GrantedAuthority> authorities = getUserAuthority(inspector.getRole());
        return new User(inspector.getEmail(), inspector.getPassword(), Collections.emptyList());

    }

    /*private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }*/


}
