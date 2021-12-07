package com.openSource.attendanceListManager.service;

import com.openSource.attendanceListManager.entity.Role;
import com.openSource.attendanceListManager.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;

@Repository
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }

    public Role findByName(String name){
        return roleRepository.findByName(name);
    };
}
