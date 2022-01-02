package com.openSource.attendanceListManager.repository;

import com.openSource.attendanceListManager.entity.ContractDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractDetailsRepository extends JpaRepository<ContractDetails, Long> {

    @Query("select cd from ContractDetails cd where cd.inspectorId = ?1")
    List<ContractDetails> findContractDetailsWithInspectorId(Long id);

    @Query(value = "select * from contract_details as cd join contracts as c on cd.contract_id=c.id where cd.inspector_id = ?1 and c.id = ?2", nativeQuery = true)
    ContractDetails findContractDetailsWithInspectorIdAndContractId(Long inspectorId, Long contractId);

    @Modifying
    @Query(value = "update contract_details set contract_id = ?1 where id = ?2", nativeQuery = true)
    void insertContractId(Long contractId, Long contractDetailsId);

    @Query(value = "select id from contract_details order by id desc limit 1", nativeQuery = true)
    Long findLastId();

    /*@Query
    List<ContractDetails> findContractDetailsByContractId(Long contractId);*/
}
