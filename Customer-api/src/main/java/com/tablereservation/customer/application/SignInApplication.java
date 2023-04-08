package com.tablereservation.customer.application;

import com.tablereservation.customer.domain.CustomerSignInForm;
import com.tablereservation.customer.domain.model.Customer;
import com.tablereservation.customer.excepition.CustomException;
import com.tablereservation.customer.service.CustomerService;
import com.tablereservation.secret.common.UserType;
import com.tablereservation.secret.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.tablereservation.customer.excepition.ErrorCode.LOGIN_CHECK_FAIL;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;
    private final JwtAuthenticationProvider provider;

    public String customerLoginToken(CustomerSignInForm form) {
        Customer c = customerService.findValidCustomer(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));

        return provider.createToken(c.getEmail(), c.getCustomer_id(), UserType.CUSTOMER);
    }
}