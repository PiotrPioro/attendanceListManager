package com.openSource.attendanceListManager.service;

import com.openSource.attendanceListManager.entity.*;
import com.openSource.attendanceListManager.repository.DaysAmountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class DaysAmountService {

    private final DaysAmountRepository daysAmountRepository;
    private final DaysService daysService;

    @Transactional
    public void addDaysAmount(DaysAmount daysAmount){
        daysAmountRepository.save(daysAmount);
    }

    @Transactional
    public void insertContractDetailsId(Long contractDetailsId){
        Integer id = daysAmountRepository.findLastId();
        daysAmountRepository.insertContractDetailsId(contractDetailsId, id);
    }

    @Transactional
    public DaysAmount findDaysAmountById(Integer id){
        return daysAmountRepository.getById(id);
    }

    @Transactional
    public void deleteDaysAmount(Integer daysAmountId){
        DaysAmount daysAmount = findDaysAmountById(daysAmountId);
        for(Days d : daysAmount.getAttendanceList()){
            daysService.deleteDay(d.getId());
        }
        daysAmountRepository.deleteById(daysAmountId);
    }

    @Transactional
    public List<DaysAmount> daysAmountList(Long contractDetailsId){
        return daysAmountRepository.findAllDaysAmountByContractDetailsId(contractDetailsId);
    }

    @Transactional
    public void editDaysAmount(ContractDetails contractDetails){
        List<DaysAmount> daysAmountList = daysAmountList(contractDetails.getId());
        for(DaysAmount daysAmount : daysAmountList){
            daysService.editDaysAmountList(daysAmount);
        }
        contractDetails.setListDaysAmount(daysAmountList);
    }

    public Integer sumOfInspectorDays(int year, int month, Inspector inspector){

        int sum = 0;
        List<Contract> contractList = inspector.getContractList();
        List<ContractDetails> contractDetailsList = new LinkedList<>();
        List<DaysAmount> daysAmountList = new LinkedList<>();

        for(Contract c: contractList){

            contractDetailsList = c.getContractDetails();
            for(ContractDetails cd: contractDetailsList){

                daysAmountList = cd.getListDaysAmount();
                for(DaysAmount da: daysAmountList){
                    if(da.getYear() == year && da.getMonthNumber() == month){

                        sum += da.getAmountOfDaysInMonth();
                    }
                }
            }
        }
        return sum;
    }

    public DaysAmount findByMonthAndYearAndContractDetailsID(int month, int year, Long contractDetailsId){
        return daysAmountRepository.findByMonthNumberAndYearAndContractDetailsId(month, year, contractDetailsId);
    }
}
