package com.tablereservation.manager.service;

import com.tablereservation.manager.application.SignInApplication;
import com.tablereservation.manager.domain.ManagerSignInForm;
import com.tablereservation.manager.domain.model.Manager;
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
    private ManagerService managerService;

    @Mock
    private JwtAuthenticationProvider provider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        signInApplication = new SignInApplication(managerService, provider);
    }

    @Test
    @DisplayName("인증 Test")
    public void testManagerLoginToken() {
        ManagerSignInForm form = new ManagerSignInForm("kys1064@naver.com", "password");
        Manager manager = Manager.builder()
                .manager_id(1L)
                .email(form.getEmail())
                .password(form.getPassword())
                .build();
        String token = "Token";

        // Mocking behavior
        when(managerService.findValidManager(form.getEmail(), form.getPassword())).thenReturn(Optional.of(manager));
        when(provider.createToken(manager.getEmail(), manager.getManager_id(), UserType.MANAGER)).thenReturn(token);

        // Test
        String result = signInApplication.managerLoginToken(form);
        assertEquals(token, result);
    }
}