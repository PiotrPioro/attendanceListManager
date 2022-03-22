package com.openSource.attendanceListManager.repository;

import com.openSource.attendanceListManager.entity.DayAmountInMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayAmountInMonthRepository extends JpaRepository<DayAmountInMonth, Long> {
}
