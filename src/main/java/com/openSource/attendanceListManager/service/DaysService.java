package com.openSource.attendanceListManager.service;

import com.openSource.attendanceListManager.entity.Days;
import com.openSource.attendanceListManager.repository.DaysRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class DaysService {

    private final DaysRepository daysRepository;

    @Transactional
    public void addDay(Days day){
        daysRepository.save(day);
    }

    @Transactional
    public List<Days> findAllDaysByDaysAmountId(int daysAmountId){
        return daysRepository.findAllByDaysAmountId(daysAmountId);
    }
}
