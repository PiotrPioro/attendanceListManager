package com.openSource.attendanceListManager.converter;

import org.springframework.core.convert.converter.Converter;
import com.openSource.attendanceListManager.entity.Role;
import com.openSource.attendanceListManager.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class RoleConverter implements Converter<String, Optional<Role>> {

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> convert(String source) {
        return roleRepository.findById(Integer.parseInt(source));
    }
}
