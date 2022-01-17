package com.openSource.attendanceListManager.repository;

import com.openSource.attendanceListManager.entity.Inspector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectorRepository extends JpaRepository<Inspector, Long> {

    @Query("select i from Inspector i where i.email = ?1")
    Inspector findByEmail(String email);

    @Query("select i from Inspector i where i.id = ?1")
    Inspector findInspectorById(Long id);
}
