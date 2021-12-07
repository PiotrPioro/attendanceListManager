package com.openSource.attendanceListManager.repository;

import com.openSource.attendanceListManager.entity.Days;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DaysRepository extends JpaRepository<Days, Integer> {

    @Query(value = "select * from days where days_amount_id=?1 order by month_day asc", nativeQuery = true)
    List<Days> findAllByDaysAmountId(int daysAmountId);
}
