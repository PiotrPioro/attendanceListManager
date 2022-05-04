package com.openSource.attendanceListManager.service;

import com.openSource.attendanceListManager.entity.DaysAmount;
import com.openSource.attendanceListManager.entity.Inspector;
import com.openSource.attendanceListManager.repository.InspectorRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InspectorService{

    private final InspectorRepository inspectorRepository;
    private final PasswordEncoder passwordEncoder;
    private final DaysAmountService daysAmountService;

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
    public void editInspector(String firstName, String lastName, int phoneNumber, String email){
        Inspector inspector = inspectorRepository.findByEmail(email);
        inspector.setFirstName(firstName);
        inspector.setLastName(lastName);
        inspector.setPhoneNumber(phoneNumber);
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

    public List<Inspector> findAllInspectors(){
        List<Inspector> inspectorList = inspectorRepository.findAll();
        return inspectorList.stream()
                .sorted(Comparator.comparing(Inspector::getLastName))
                .collect(Collectors.toList());
    }
}
