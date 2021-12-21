package com.openSource.attendanceListManager.repository;

import com.openSource.attendanceListManager.entity.MonthsName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthNameRepository extends JpaRepository<MonthsName, Integer> {

    MonthsName findMonthNameById(Integer id);
}