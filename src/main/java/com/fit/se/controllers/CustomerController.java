package com.fit.se.controllers;


import com.fit.se.models.Customer;
import com.fit.se.repositories.CustomerRepository;
import com.fit.se.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @GetMapping("")
    public String showCustomerListPaging(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("keyword") Optional<String> keyWord

        ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Customer> customerPage = customerService.findPaginated(
                PageRequest.of(currentPage - 1, pageSize),keyWord
        );

        int totalPages = customerPage.getTotalPages();
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(startPage + 4, totalPages);
        model.addAttribute("customerPage", customerPage);
        model.addAttribute("control","customers");
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(startPage,endPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin/customer/customer-page";
    }

    @GetMapping("/insertForm")
    public String getInsertCustomerPage(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "admin/customer/insert-customer";
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute("customer") Customer customer,
                              BindingResult result, Model model) {
        customerRepository.save(customer);
        return "redirect:/admin/customers";
    }
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        customerRepository.delete(customer);
        return "redirect:/admin/customers";
    }

//    Get info customer need edit
    @GetMapping("/showEdit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("customer", customer);
        return "admin/customer/edit-customer";
    }
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Validated Customer customer,
                             BindingResult result) {
        if (result.hasErrors()) {
            customer.setId(id);
            return "admin/customer/edit-customer";
        }
        customerRepository.save(customer);
        return "redirect:/admin/customers";
    }
}
