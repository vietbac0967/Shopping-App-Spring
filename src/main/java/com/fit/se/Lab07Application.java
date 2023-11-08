package com.fit.se;

import com.fit.se.enums.EmployeeStatus;
import com.fit.se.enums.ProductStatus;
import com.fit.se.models.*;
import com.fit.se.repositories.*;
import net.datafaker.Faker;
import net.datafaker.providers.base.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

//    Faker faker = new Faker();

    public static void main(String[] args) {
        SpringApplication.run(Lab07Application.class, args);
    }

//    public LocalDateTime generateRandomDateTime() {
//        long minDay = LocalDate.of(2020, 1, 1).toEpochDay();
//        long maxDay = LocalDate.of(2023, 12, 30).toEpochDay();
//        long randomDay = faker.number().numberBetween(minDay, maxDay);
//
//        LocalTime randomTime = LocalTime.of(faker.number().numberBetween(0, 24), faker.number().numberBetween(0, 60));
//
//        return LocalDate.ofEpochDay(randomDay).atTime(randomTime);
//    }

//    @Bean
//    CommandLineRunner commandLineRunner() {
//        return args -> {

//            for (long i = 1; i <= 200; i++) {
//                String name = faker.commerce().productName();
//                String description = faker.lorem().sentence();
//                String unit = faker.commerce().material();
//                String manufacturer = faker.company().name();
//                Product product = new Product(name,description,unit,manufacturer,ProductStatus.ACTIVE);
//                productRepository.save(product);
//            }
//            for (int i = 0; i < 200; i++) {
//                String name = faker.name().fullName();
//                String email = faker.internet().emailAddress();
//             String phone = faker.phoneNumber().cellPhone();
//                String address = faker.address().fullAddress();
//
//                Customer customer = new Customer(name, email, phone, address);
//                customerRepository.save(customer);
//            }
//            for (long i = 1;i<=200;i++){
//                String path = faker.internet().image();
//                String alternative = faker.lorem().sentence();
//                Product product = new Product(i);
//                ProductImage productImage = new ProductImage(path,alternative,product);
//                productImageRepository.save(productImage);
//            }

//            long id = 1; // Initialize id outside the loop
//
//            for (long i = 1; i <= 150; i++) {
//                LocalDateTime priceDateTime = generateRandomDateTime();
//                double price = faker.number().randomDouble(2, 50, 1000);
//                String note = faker.lorem().sentence();
//                ProductPrice productPrice = new ProductPrice(new Product(id), priceDateTime, price, note);
//                productPriceRepository.save(productPrice);
//
//                if (i % 3 == 0) {
//                    id++; // Increment id every 3rd iteration
//                }
//            }


//            ProductPrice productPrice = new ProductPrice();
//            productPrice.setProduct(new Product(1L));
//            productPrice.setPriceDateTime(LocalDateTime.now());
//            productPrice.setPrice(64712.5);
//            productPrice.setNote("241241241");
//            productPriceRepository.save(productPrice);
//            for (long i = 1;i<=200;i++){
//                Employee employee = Employee.builder()
//                        .address(faker.address().fullAddress())
//                        .dob(faker.date().birthday().toLocalDateTime().toLocalDate())
//                        .email(faker.internet().emailAddress())
//                        .fullName(faker.name().fullName())
//                        .phone(faker.phoneNumber().cellPhone())
//                        .status(EmployeeStatus.ACTIVE)
//                        .build();
//                employeeRepository.save(employee);
//            }
//        };
//    }
}
