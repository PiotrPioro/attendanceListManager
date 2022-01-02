package com.openSource.attendanceListManager.service;

import com.openSource.attendanceListManager.entity.Contract;
import com.openSource.attendanceListManager.entity.ContractDetails;
import com.openSource.attendanceListManager.entity.DaysAmount;
import com.openSource.attendanceListManager.repository.ContractDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ContractDetailService {

    private final ContractDetailsRepository contractDetailsRepository;
    private final DaysAmountService daysAmountService;

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
        ContractDetails contractDetails = findContractDetailsById(id);
        for(DaysAmount da : contractDetails.getListDaysAmount()){
            daysAmountService.deleteDaysAmount(da.getId());
        }
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

/*    @Transactional
    public List<ContractDetails> findContractDetailsByContractId(Long contractId){
        return contractDetailsRepository.findContractDetailsByContractId(contractId);
    }*/

/*    @Transactional
    public void editContractDetails(Contract contract){
        List<ContractDetails> contractDetailsList = findContractDetailsByContractId(contract.getId());
        for(ContractDetails contractDetails : contractDetailsList){
            daysAmountService.editDaysAmount(contractDetails);
        }
        contract.setContractDetails(contractDetailsList);
    }*/
}
