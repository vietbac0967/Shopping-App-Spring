package com.fit.se;

import com.fit.se.enums.EmployeeStatus;
import com.fit.se.enums.ProductStatus;
import com.fit.se.models.*;
import com.fit.se.repositories.*;
import com.fit.se.services.OrderService;
import com.fit.se.services.ProductPriceService;
import net.datafaker.Faker;
import net.datafaker.providers.base.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.security.Timestamp;
import java.time.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class Lab07Application {
//    @Autowired
//    private CustomerRepository customerRepository;
//    @Autowired
//    private ProductRepository productRepository;

//    @Autowired
//    private ProductImageRepository productImageRepository;

//    @Autowired
//    private ProductPriceRepository productPriceRepository;

    //    @Autowired
//    private EmployeeRepository employeeRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    ProductPriceService productPriceService;

    Faker faker = new Faker();

    @Autowired
    OrderService orderService;


    public static void main(String[] args) {
        SpringApplication.run(Lab07Application.class, args);
    }


//    @Bean
//    CommandLineRunner commandLineRunner() {
//        return args -> {
//            LocalDateTime fromDate = LocalDateTime.of(LocalDate.of(2021, 10, 20), LocalTime.MIN);
//            LocalDateTime toDate = LocalDateTime.of(LocalDate.of(2022, 10, 12), LocalTime.MIN);
//            List<Order> orders = orderRepository.getOrderByDate(fromDate,toDate);
//            orders.forEach(System.out::println);
//        };
//    }
}
