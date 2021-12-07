package com.openSource.attendanceListManager.service;

import com.openSource.attendanceListManager.entity.Inspector;
import com.openSource.attendanceListManager.repository.InspectorRepository;
import com.openSource.attendanceListManager.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class InspectorService{

    private final InspectorRepository inspectorRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void addInspector(Inspector inspector){
        inspectorRepository.save(inspector);
    }

    @Transactional
    public Inspector findByEmail(String email){
        return inspectorRepository.findByEmail(email);
    }

    @Transactional
    public Inspector findById(Long id){
        return inspectorRepository.findInspectorById(id);
    }

    @Transactional
    public void editInspector(String firstName, String lastName, String email){
        Inspector inspector = inspectorRepository.findByEmail(email);
        inspector.setFirstName(firstName);
        inspector.setLastName(lastName);
        inspectorRepository.save(inspector);
    }

    @Transactional
    public void editPassword(String password, String email){
        Inspector inspector = inspectorRepository.findByEmail(email);
        inspector.setPassword(passwordEncoder.encode(password));
        inspectorRepository.save(inspector);
    }

    @Transactional
    public void deleteInspector(String email){
        Inspector inspector = inspectorRepository.findByEmail(email);
        inspectorRepository.delete(inspector);
    }

    @Transactional
    public List<Inspector> findAllInspectors(){
        return inspectorRepository.findAll();
    }
}
