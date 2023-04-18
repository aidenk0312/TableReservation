package com.tablereservation.customer.service;

import com.tablereservation.customer.domain.CustomerSignUpForm;
import com.tablereservation.customer.domain.model.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SignUpTest {

    @Autowired
    private SignUpCustomerService service;

    @Test
    @DisplayName("회원가입 테스트")
    void signUp() {
        CustomerSignUpForm form = CustomerSignUpForm.builder()
                .name("kim")
                .birth(LocalDate.now())
                .email("abc1222@test.com")
                .password("1")
                .phone("01011111112")
                .build();
        Customer c = service.signUp(form);

        assertNotNull(c.getCustomer_id());
        assertNotNull(c.getCreatedAt());
    }
}