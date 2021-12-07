package com.openSource.attendanceListManager.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CurrentInspector extends User {
    public CurrentInspector(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

    }

/*    public CurrentInspector(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }*/
}
