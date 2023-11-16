package com.fit.se.controllers;


import com.fit.se.models.Customer;
import com.fit.se.models.Employee;
import com.fit.se.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/employees")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("")
    public String showCustomerListPaging(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("keyword") Optional<String> keyword) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Employee> employeePage = employeeService.findPaginated(
                PageRequest.of(currentPage - 1, pageSize),keyword
        );
        int totalPages = employeePage.getTotalPages();
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(startPage + 4, totalPages);
        model.addAttribute("employeePage", employeePage);
        model.addAttribute("control","employees");
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(startPage,endPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin/employee/employee-page";
    }

}
