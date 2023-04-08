package com.tablereservation.manager.service;


import com.tablereservation.manager.domain.ManagerSignUpForm;
import com.tablereservation.manager.domain.model.Manager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SignUpTest {

    @Autowired
    private SignUpManagerService service;

    @Test
    @DisplayName("회원가입 테스트")
    void signUp() {
        ManagerSignUpForm form = ManagerSignUpForm.builder()
                .name("kim")
                .birth(LocalDate.now())
                .email("abc111@test.com")
                .password("1")
                .phone("01011111111")
                .build();
        Manager m = service.signUp(form);

        assertNotNull(m.getManager_id());
        assertNotNull(m.getCreatedAt());
    }
}