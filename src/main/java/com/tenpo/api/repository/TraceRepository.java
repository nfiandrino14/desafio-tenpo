package com.tenpo.api.repository;

import com.tenpo.api.domain.Trace;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TraceRepository extends PagingAndSortingRepository<Trace, Long> {
}
