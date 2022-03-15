package com.openSource.attendanceListManager.repository;

import com.openSource.attendanceListManager.entity.WorkingDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingDayRepository extends JpaRepository<WorkingDays, Long> {
}
