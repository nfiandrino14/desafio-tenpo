package com.tenpo.api.services;

import com.tenpo.api.dto.ResultResponseDTO;
import com.tenpo.api.service.CalculatorService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CalculatorServiceTest {

    private final CalculatorService calculator = new CalculatorService();

    @Test
    public void sum() {
        double a = 100.0;
        double b = 200.0;
        ResultResponseDTO result = calculator.sum(a, b);
        ResultResponseDTO expectedResult = new ResultResponseDTO(300.0);
        assertEquals(result, expectedResult);
    }

}
