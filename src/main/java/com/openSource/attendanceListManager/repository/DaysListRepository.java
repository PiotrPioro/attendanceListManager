package com.openSource.attendanceListManager.repository;

import com.openSource.attendanceListManager.entity.DaysList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaysListRepository extends JpaRepository<DaysList, Long> {
}
