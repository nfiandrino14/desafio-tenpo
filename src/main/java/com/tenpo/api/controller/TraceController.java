package com.tenpo.api.controller;

import com.tenpo.api.domain.Trace;
import com.tenpo.api.service.TraceService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/trace")
public class TraceController {

    private final TraceService traceService;

    @GetMapping(value = "/history")
    public ResponseEntity<List<Trace>> getHistoryTrace(@RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<Trace> tracesResponse = traceService.getTraces(pageNumber, pageSize, sortBy);
        return ResponseEntity.ok(tracesResponse);
    }
}
