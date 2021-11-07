package com.tenpo.api.repository;

import com.tenpo.api.domain.Trace;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "management.trace.http", name = "enabled", matchIfMissing = true)
@Slf4j
public class CustomHttpTraceRepository implements HttpTraceRepository {

    @Autowired
    private TraceRepository traceRepository;
    
    @Override
    public List<HttpTrace> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void add(HttpTrace httpTrace) {
        Trace trace = new Trace();
        trace.setMethod(httpTrace.getRequest().getMethod());
        trace.setEndpoint(httpTrace.getRequest().getUri().toString());
        trace.setStatusCode(httpTrace.getResponse().getStatus());
        trace.setTimeStamp(httpTrace.getTimestamp().toString());
        traceRepository.save(trace);
    }
    
}
