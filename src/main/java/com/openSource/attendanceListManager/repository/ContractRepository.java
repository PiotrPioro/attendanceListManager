package com.openSource.attendanceListManager.repository;

import com.openSource.attendanceListManager.entity.Contract;
import com.openSource.attendanceListManager.entity.Inspector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    Contract findContractById(Long id);

    List<Contract> findContractByContractAdministrator(Inspector inspector);
}
