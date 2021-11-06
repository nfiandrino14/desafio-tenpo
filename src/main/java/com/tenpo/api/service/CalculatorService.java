package com.tenpo.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.tenpo.api.dto.ResultResponseDTO;

@Service
@Slf4j
public class CalculatorService {

    public ResultResponseDTO sum(double a, double b) {
        log.info("Processing Sum: {} + {}", a, b);
        return new ResultResponseDTO(a + b);
    }

}
