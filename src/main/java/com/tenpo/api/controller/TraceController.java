package com.tenpo.api.controller;

import com.tenpo.api.domain.Trace;
import com.tenpo.api.service.TraceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/trace")
@Api(tags = "Traces")
public class TraceController {

    private final TraceService traceService;

    @GetMapping(value = "/history")
    @ApiOperation(value = "Get History")
    public ResponseEntity<List<Trace>> getHistoryTrace(
            @RequestHeader(required = true, value = "Authorization") String auth,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<Trace> tracesResponse = traceService.getTraces(pageNumber, pageSize, sortBy);
        return ResponseEntity.ok(tracesResponse);
    }
}
