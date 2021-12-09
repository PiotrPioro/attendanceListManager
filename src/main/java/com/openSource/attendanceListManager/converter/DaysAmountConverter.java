package com.openSource.attendanceListManager.converter;

import com.openSource.attendanceListManager.entity.DaysAmount;
import com.openSource.attendanceListManager.repository.DaysAmountRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@AllArgsConstructor
public class DaysAmountConverter implements Converter<String, Optional<DaysAmount>> {

    private final DaysAmountRepository daysAmountRepository;

    @Override
    public Optional<DaysAmount> convert(String source) {
        return daysAmountRepository.findById(Integer.parseInt(source));
    }
}
