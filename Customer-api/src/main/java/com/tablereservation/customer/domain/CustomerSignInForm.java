package com.tablereservation.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerSignInForm {
    private String email;
    private String password;
}