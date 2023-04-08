package com.tablereservation.customer.controller;

import com.tablereservation.customer.application.SignInApplication;
import com.tablereservation.customer.domain.CustomerSignInForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/signIn")
@RequiredArgsConstructor
public class SignInController {

    private final SignInApplication signInApplication;

    @PostMapping("/customer")
    public ResponseEntity<String> signInCustomer(@RequestBody CustomerSignInForm form) {
        return ResponseEntity.ok(signInApplication.customerLoginToken(form));
    }

}