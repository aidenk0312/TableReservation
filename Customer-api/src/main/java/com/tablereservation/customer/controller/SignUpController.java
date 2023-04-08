package com.tablereservation.customer.controller;

import com.tablereservation.customer.application.SignUpApplication;
import com.tablereservation.customer.domain.CustomerSignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpApplication signUpApplication;

    @PostMapping("/customer")
    public ResponseEntity<String> customerSignUp(@RequestBody CustomerSignUpForm form) {
        String result = signUpApplication.customerSignUp(form);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/customer/verify")
    public ResponseEntity<String> verifyCustomer(String email, String code) {
        signUpApplication.customerVerify(email, code);
        return ResponseEntity.ok("인증 완료");
    }
}