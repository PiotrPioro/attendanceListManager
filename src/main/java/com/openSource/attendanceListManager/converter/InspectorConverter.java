package com.openSource.attendanceListManager.converter;

import com.openSource.attendanceListManager.entity.Inspector;
import com.openSource.attendanceListManager.repository.InspectorRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class InspectorConverter implements Converter<String, Optional<Inspector>> {

    private final InspectorRepository inspectorRepository;

    @Override
    public Optional<Inspector> convert(String source) {
        return inspectorRepository.findById(Long.parseLong(source));
    }
}
