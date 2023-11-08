package com.fit.se;

import com.fit.se.models.Customer;
import com.fit.se.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void saveCustomerTest() {
        Customer customer = Customer.builder()
                .address("Nguyen Van Bao Go Vap")
                .email("quoctuan@gmail.com")
                .name("Tran Quoc Tuan")
                .phone("082595712512")
                .build();
        customerRepository.save(customer);
        Assertions.assertThat(customer.getId()).isGreaterThan(0);
    }
}