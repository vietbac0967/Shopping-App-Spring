package com.fit.se.controllers;


import com.fit.se.models.Customer;
import com.fit.se.models.Order;
import com.fit.se.models.Product;
import com.fit.se.repositories.*;
import com.fit.se.services.OrderService;
import com.fit.se.services.ProductPriceService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final ProductPriceService productPriceService;

    @GetMapping("/orders")
    public String getAdminPage(Model model,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size
    ) {
        List<Product> products = productRepository.findProducts();
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        Page<Order> orderPage = orderService.findPaginated(
                PageRequest.of(currentPage - 1, pageSize));
        int totalPages = orderPage.getTotalPages();
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(startPage + 4, totalPages);
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("control", "orders");
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("sizeProduct", products.size());
        model.addAttribute("sizeOrder", orderRepository.count());
        model.addAttribute("orderDetailRepository", orderDetailRepository);
        model.addAttribute("customerRepository", customerRepository);
        model.addAttribute("employeeRepository", employeeRepository);
        return "admin/admin-page";
    }


    public Map<Object, Object> createDataPoint(LocalDateTime x, double y) {
        Map<Object, Object> map = new HashMap<>();
        map.put("label", x.toString());
        map.put("y", y);
        return map;
    }

    @GetMapping("/chart-page")
    public String chart(Model model) {

        List<Product> products = productRepository.findProducts();
        model.addAttribute("products", products);
        return "admin/report/chart";
    }

    @GetMapping("/show-chart")
    public String showPaintChart(Model model, @RequestParam("id") long id) {
        Gson gsonObj = new Gson();
        List<Map<Object, Object>> list = new ArrayList<>();
        List<Product> products = productRepository.findProducts();
        Map<LocalDateTime, Double> map = productPriceService.getPriceFollowData(id);
        for (LocalDateTime key : map.keySet()) {
            Map<Object, Object> data = createDataPoint(key, map.get(key));
            list.add(data);
        }
        String dataPoints = gsonObj.toJson(list);
        model.addAttribute("dataPoints", dataPoints);
        model.addAttribute("products", products);
        return "admin/report/chart";
    }
}
