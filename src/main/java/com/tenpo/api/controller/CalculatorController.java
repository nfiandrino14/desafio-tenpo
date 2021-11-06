package com.tenpo.api.controller;

import com.tenpo.api.dto.ResultResponseDTO;
import com.tenpo.api.service.CalculatorService;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private final CalculatorService calculatorService;

    @GetMapping("/sum")
    public ResponseEntity<ResultResponseDTO> sum(@RequestParam(required = true) @NotEmpty double numberA,
            @RequestParam(required = true) @NotEmpty double numberB) {
        return ResponseEntity.ok(calculatorService.sum(numberA, numberB));
    }
}
