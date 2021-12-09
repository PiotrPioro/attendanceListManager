package com.openSource.attendanceListManager.service;

import com.openSource.attendanceListManager.entity.Contract;
import com.openSource.attendanceListManager.entity.ContractDetails;
import com.openSource.attendanceListManager.entity.Inspector;
import com.openSource.attendanceListManager.repository.ContractDetailsRepository;
import com.openSource.attendanceListManager.repository.ContractRepository;
import com.openSource.attendanceListManager.repository.InspectorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractDetailService contractDetailsService;
    private final InspectorRepository inspectorRepository;
    private final ContractDetailsRepository contractDetailsRepository;

    @Transactional
    public List<Contract> findAllContracts(){
        return contractRepository.findAll();
    }

    @Transactional
    public void addContract(Contract contract){
        contractRepository.save(contract);
    }

    @Transactional
    public Contract findContractById(Long id){
        return contractRepository.findContractById(id);
    }

    @Transactional
    public Map<Contract, ContractDetails> contractMap(Long inspectorId){

        Map<Contract, ContractDetails> map = new HashMap<>();
        Inspector inspector = inspectorRepository.findInspectorById(inspectorId);
        List<Contract> contractList = inspector.getContractList();
        for(Contract c : contractList){
            map.put(c, contractDetailsRepository.findContractDetailsWithInspectorIdAndContractId(inspectorId, c.getId()));
        }
        return map;
    }

    @Transactional
    public void deleteContract(Long id){
        contractRepository.deleteById(id);
    }

    @Transactional
    public List<Contract> findContractByContractAdministrator(Inspector inspector){
        return contractRepository.findContractByContractAdministrator(inspector);
    }

    @Transactional
    public Map<Inspector, ContractDetails> inspectorAndDetailsMap(Long contractId){
        List<Inspector> inspectorList = contractRepository.findContractById(contractId).getInspectorList();
        Map<Inspector, ContractDetails> inspectorDetailsMap = new HashMap<>();
        for(Inspector i : inspectorList){
            inspectorDetailsMap.put(i, contractDetailsService.findContractDetailsByInspectorIdAndContractId(i.getId(), contractId));
        }
        return inspectorDetailsMap;
    }
}
