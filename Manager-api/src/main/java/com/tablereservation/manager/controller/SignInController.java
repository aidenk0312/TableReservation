package com.tablereservation.manager.controller;

import com.tablereservation.manager.application.SignInApplication;
import com.tablereservation.manager.domain.ManagerSignInForm;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping("/manager")
    @ApiOperation("로그인 API")
    public ResponseEntity<String> signInManager(@RequestBody ManagerSignInForm form) {
        return ResponseEntity.ok(signInApplication.managerLoginToken(form));
    }
}