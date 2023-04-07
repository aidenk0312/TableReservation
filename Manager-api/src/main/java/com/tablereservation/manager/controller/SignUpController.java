package com.tablereservation.manager.controller;

import com.tablereservation.manager.application.SignUpApplication;
import com.tablereservation.manager.domain.ManagerSignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpApplication signUpApplication;

    @PostMapping("/manager")
    public ResponseEntity<String> managerSignUp(@RequestBody ManagerSignUpForm form) {
        String result = signUpApplication.managerSignUp(form);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/manager/verify")
    public ResponseEntity<String> verifyCustomer(String email, String code) {
        signUpApplication.managerVerify(email, code);
        return ResponseEntity.ok("인증 완료");
    }
}