package com.openSource.attendanceListManager.service;

import com.openSource.attendanceListManager.entity.Contract;
import com.openSource.attendanceListManager.entity.ContractDetails;
import com.openSource.attendanceListManager.entity.Inspector;
import com.openSource.attendanceListManager.repository.ContractRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractDetailService contractDetailsService;
    private final InspectorService inspectorService;

    public List<Contract> findAllContracts(){
        List<Contract> contractList = contractRepository.findAll();
        return contractList.stream()
                .sorted(Comparator.comparing(Contract::getName))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addContract(Contract contract){
        contractRepository.save(contract);
    }

    public Contract findContractById(Long id){
        return contractRepository.findContractById(id);
    }

    public Map<Contract, ContractDetails> contractMap(Long inspectorId){

        Map<Contract, ContractDetails> map = new LinkedHashMap<>();
        Inspector inspector = inspectorService.findById(inspectorId);
        List<Contract> contractList = inspector.getContractList()
                .stream()
                .sorted(Comparator.comparing(Contract::getName))
                .collect(Collectors.toList());
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

    public List<Contract> findContractByContractAdministrator(Inspector inspector){
        List<Contract> contractList = contractRepository.findContractByContractAdministrator(inspector);
        return contractList.stream()
                .sorted(Comparator.comparing(Contract::getName))
                .collect(Collectors.toList());
    }

    public Map<Inspector, ContractDetails> inspectorAndDetailsMap(Long contractId){
        List<Inspector> inspectorList = contractRepository.findContractById(contractId).getInspectorList();
        List<Inspector> sortedList = inspectorList.stream()
                .sorted(Comparator.comparing(Inspector::getLastName)
                .thenComparing(Inspector::getFirstName))
                .collect(Collectors.toList());
        Map<Inspector, ContractDetails> inspectorDetailsMap = new LinkedHashMap<>();
        for(Inspector i : sortedList){
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

    public List<Inspector> allInspectorExceptAlreadyConnected(Contract contract){
        List<Inspector> inspectorList = inspectorService.findAllInspectors();
        List<Inspector> contractInspectorList = contract.getInspectorList();
        return inspectorList.stream()
                .filter(s -> !contractInspectorList.contains(s))
                .collect(Collectors.toList());
    }

    //przekazuję listę inspektorów którą chcę usunąć i kontrakt z którego chcę usunąć inspektorów
    @Transactional
    public void deleteInspectorFromContract(List<Inspector> inspectorList, Contract contract){

        List<Inspector> inspectorList1 = contract.getInspectorList();
        for(Inspector i : inspectorList){
            List<Contract> contractList = i.getContractList();
            List<Contract> contractList1 = contractList.stream()
                    .filter(c -> c.getId() != contract.getId())
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
