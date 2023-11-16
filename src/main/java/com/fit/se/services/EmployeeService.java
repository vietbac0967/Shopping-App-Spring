package com.fit.se.services;

import com.fit.se.enums.EmployeeStatus;
import com.fit.se.enums.ProductStatus;
import com.fit.se.models.Employee;
import com.fit.se.models.Product;
import com.fit.se.repositories.EmployeeRepository;
import com.fit.se.utils.PageRender;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Page<Employee> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return employeeRepository.findAll(pageable);
    }

    public Page<Employee> findPaginated(Pageable pageable,Optional<String> keyword) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Employee> employees;
        if(keyword.isPresent()){
            employees = employeeRepository.findByKeyword(keyword.get());
        }else {
            employees = employeeRepository.findEmployees();
        }
        PageRender<Employee> employeePageRender = new PageRender<>();
        List<Employee> list = employeePageRender.getPageOfModel(employees.size(),startItem,pageSize,employees);
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), employees.size());
    }
    public void deleteEmployee(long id){
        Optional<Employee> optional = employeeRepository.findById(id);
        if(optional.isPresent()){
            Employee employee = optional.get();
            employee.setId(id);
            employee.setStatus(EmployeeStatus.TERMINATED);
            employeeRepository.save(employee);
        }
    }
}
