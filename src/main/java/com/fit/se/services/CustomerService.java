package com.fit.se.services;

import com.fit.se.models.Customer;
import com.fit.se.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Page<Customer> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        return customerRepository.findAll(pageable);
    }

    public Page<Customer> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Customer> list;
        List<Customer> candidates = customerRepository.findAll();

        if (candidates.size() >= startItem) {
            int toIndex = Math.min(startItem + pageSize, candidates.size());
            list = candidates.subList(startItem, toIndex);
        } else {
            list = Collections.emptyList();
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), candidates.size());
    }
}
