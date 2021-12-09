package com.openSource.attendanceListManager.service;

import com.openSource.attendanceListManager.entity.DaysAmount;
import com.openSource.attendanceListManager.repository.DaysAmountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class DaysAmountService {

    private final DaysAmountRepository daysAmountRepository;

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
        daysAmountRepository.deleteById(daysAmountId);
    }

    @Transactional
    public List<DaysAmount> daysAmountList(Long contractDetailsId){
        return daysAmountRepository.findAllDaysAmountByContractDetailsId(contractDetailsId);
    }
}
