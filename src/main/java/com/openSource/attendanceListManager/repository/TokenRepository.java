package com.openSource.attendanceListManager.repository;

import com.openSource.attendanceListManager.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByName(String name);
}
