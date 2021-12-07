package com.openSource.attendanceListManager.converter;

import com.openSource.attendanceListManager.entity.Contract;
import com.openSource.attendanceListManager.repository.ContractRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ContractConverter implements Converter<String, Optional<Contract>> {

    private final ContractRepository contractRepository;

    @Override
    public Optional<Contract> convert(String source) {
        return contractRepository.findById(Long.parseLong(source));
    }
}