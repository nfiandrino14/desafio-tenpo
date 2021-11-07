package com.tenpo.api.service;

import com.tenpo.api.domain.Trace;
import com.tenpo.api.repository.TraceRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TraceService {
    
    private final TraceRepository repository;
    
      public List<Trace> getTraces(Integer pageNumber, Integer pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Trace> pagedResult = repository.findAll(paging);
        return pagedResult.hasContent() ? pagedResult.getContent() : List.of();
    }
    
    
}
