package com.tenpo.api.controller;

import com.tenpo.api.dto.ResultResponseDTO;
import com.tenpo.api.service.CalculatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Api(tags = "Calculator")
@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private final CalculatorService calculatorService;

    @GetMapping("/sum")
    @ApiOperation(value = "Suma")
    public ResponseEntity<ResultResponseDTO> sum(
            @RequestHeader(required = true, value = "Authorization") String headerAuth,
            @RequestParam(required = true) @NotEmpty double numberA,
            @RequestParam(required = true) @NotEmpty double numberB) {
        return ResponseEntity.ok(calculatorService.sum(numberA, numberB));
    }
}
