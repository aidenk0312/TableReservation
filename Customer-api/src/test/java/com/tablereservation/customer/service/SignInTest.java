package com.tablereservation.customer.service;

import com.tablereservation.customer.application.SignInApplication;
import com.tablereservation.customer.domain.CustomerSignInForm;
import com.tablereservation.customer.domain.model.Customer;
import com.tablereservation.secret.common.UserType;
import com.tablereservation.secret.config.JwtAuthenticationProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class SignInTest {

    private SignInApplication signInApplication;

    @Mock
    private CustomerService customerService;

    @Mock
    private JwtAuthenticationProvider provider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        signInApplication = new SignInApplication(customerService, provider);
    }

    @Test
    @DisplayName("인증 Test")
    public void testCustomerLoginToken() {
        CustomerSignInForm form = new CustomerSignInForm("kys1064@naver.com", "password");
        Customer customer = Customer.builder()
                .customer_id(1L)
                .email(form.getEmail())
                .password(form.getPassword())
                .build();
        String token = "Token";

        when(customerService.findValidCustomer(form.getEmail(), form.getPassword())).thenReturn(Optional.of(customer));
        when(provider.createToken(customer.getEmail(), customer.getCustomer_id(), UserType.CUSTOMER)).thenReturn(token);

        String result = signInApplication.customerLoginToken(form);
        assertEquals(token, result);
    }
}