package com.openSource.attendanceListManager.service;

import com.openSource.attendanceListManager.entity.ContractDetails;
import com.openSource.attendanceListManager.repository.ContractDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ContractDetailService {

    private final ContractDetailsRepository contractDetailsRepository;

    @Transactional
    public void addContractDetails(ContractDetails contractDetails){
        contractDetailsRepository.save(contractDetails);
    }

    @Transactional
    public List<ContractDetails> findContractDetailsByInspectorId(Long inspectorId){
        return contractDetailsRepository.findContractDetailsWithInspectorId(inspectorId);
    }

    @Transactional
    public void deleteContractDetails(Long id){
        contractDetailsRepository.deleteById(id);
    }

    @Transactional
    public ContractDetails findContractDetailsById(Long id){
        return contractDetailsRepository.getById(id);
    }

    @Transactional
    public ContractDetails findContractDetailsByInspectorIdAndContractId(Long inspectorId, Long contractId){
        return contractDetailsRepository.findContractDetailsWithInspectorIdAndContractId(inspectorId, contractId);
    }

    @Transactional
    public void insertContractId(Long contractId){
        contractDetailsRepository.insertContractId(contractId, contractDetailsRepository.findLastId());
    }
}
