package com.openSource.attendanceListManager.repository;

import com.openSource.attendanceListManager.entity.Contract;
import com.openSource.attendanceListManager.entity.Inspector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    Contract findContractById(Long id);

    List<Contract> findContractByContractAdministrator(Inspector inspector);

/*    @Modifying
    @Query(value = "delete inspectors_contract_list where contract_id = ?1", nativeQuery = true)
    void deleteInspectorContract(Long contractId);*/
}
