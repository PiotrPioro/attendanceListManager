package com.openSource.attendanceListManager.repository;

import com.openSource.attendanceListManager.entity.DaysAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DaysAmountRepository extends JpaRepository<DaysAmount, Integer> {

    Optional<DaysAmount> findById(int id);

    @Query(value = "select id from days_amount order by id desc limit 1", nativeQuery = true)
    Integer findLastId();

    @Modifying
    @Query(value = "update days_amount set contract_details_id = ?1 where id = ?2", nativeQuery = true)
    void insertContractDetailsId(Long contractDetails, Integer daysAmountId);

    @Query(value = "select * from days_amount where contract_details_id=?1", nativeQuery = true)
    List<DaysAmount> findAllDaysAmountByContractDetailsId(Long contractDetailsId);
}
