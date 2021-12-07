package com.openSource.attendanceListManager.converter;

import com.openSource.attendanceListManager.entity.ContractDetails;
import com.openSource.attendanceListManager.repository.ContractDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ContractDetailsConverter implements Converter<String, Optional<ContractDetails>> {

    private final ContractDetailsRepository contractDetailsRepository;

    @Override
    public Optional<ContractDetails> convert(String source) {
        return contractDetailsRepository.findById(Long.parseLong(source));
    }
}
