package com.tenpo.api.services;

import com.tenpo.api.domain.Trace;
import com.tenpo.api.repository.TraceRepository;
import com.tenpo.api.service.TraceService;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class TraceServiceTest {

    private TraceService service;
    private TraceRepository repository;

    @BeforeEach
    public void setUp() {
        repository = mock(TraceRepository.class);
        service = new TraceService(repository);
    }

    @Test
    void getTraces() {
        Trace trace = new Trace("/test", 200, "11111", "GET");

        Page<Trace> traces = mock(Page.class);
        when(repository.findAll(any(Pageable.class))).thenReturn(traces);
        when(traces.hasContent()).thenReturn(true);
        when(traces.getContent()).thenReturn(List.of(trace));

        List<Trace> results = service.getTraces(0, 10, "id");
        assertEquals(results.size(), 1);
        assertEquals(results.get(0), trace);

    }

    @Test
    void getEmptyTrace() {
        Page<Trace> traces = mock(Page.class);
        when(repository.findAll(any(Pageable.class))).thenReturn(traces);
        when(traces.hasContent()).thenReturn(false);
        when(traces.getContent()).thenReturn(List.of());

        List<Trace> results = service.getTraces(0, 10, "id");
        assertEquals(results.size(), 0);
    }

}
