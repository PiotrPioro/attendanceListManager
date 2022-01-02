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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractDetailService contractDetailsService;
    private final InspectorService inspectorService;

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
        Inspector inspector = inspectorService.findById(inspectorId);
        List<Contract> contractList = inspector.getContractList();
        for(Contract c : contractList){
            map.put(c, contractDetailsService.findContractDetailsByInspectorIdAndContractId(inspectorId, c.getId()));
        }
        return map;
    }

    @Transactional
    public void deleteContract(Long id){
        Contract contract = findContractById(id);
        for(ContractDetails cd : contract.getContractDetails()){
            contractDetailsService.deleteContractDetails(cd.getId());
        }

        for(Inspector inspector : contract.getInspectorList()){
            inspector.getContractList().remove(contract);
        }
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

    @Transactional
    public void addInspectors(Contract contract){
        List<Inspector> inspectorList = contract.getInspectorList();
        for(Inspector i : inspectorList){
            List<Contract> contractList = i.getContractList();
            contractList.add(contract);
            i.setContractList(contractList);
            inspectorService.addInspector(i);
        }
    }

    @Transactional
    public List<Inspector> allInspectorExceptAlreadyConnected(Contract contract){
        List<Inspector> inspectorList = inspectorService.findAllInspectors();
        List<Inspector> contractInspectorList = contract.getInspectorList();
        return inspectorList.stream()
                .filter(s -> !contractInspectorList.contains(s))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteInspectorFromContract(List<Inspector> inspectorList, Contract contract){
        List<Inspector> inspectorList1 = contract.getInspectorList();
        for(Inspector i : inspectorList){
            List<Contract> contractList = i.getContractList();
            List<Contract> contractList1 = contractList.stream()
                    .filter(c -> !contractList.contains(contract))
                    .collect(Collectors.toList());
            i.setContractList(contractList1);
            inspectorService.addInspector(i);
        }
        List<Inspector> inspectorList2 = inspectorList1.stream()
                .filter(i -> !inspectorList.contains(i))
                .collect(Collectors.toList());
        contract.setInspectorList(inspectorList2);
        contractRepository.save(contract);
    }
}
