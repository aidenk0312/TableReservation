package com.tablereservation.manager.application;


import com.tablereservation.manager.domain.ManagerSignInForm;
import com.tablereservation.manager.domain.model.Manager;
import com.tablereservation.manager.excepition.CustomException;
import com.tablereservation.manager.service.ManagerService;
import com.tablereservation.secret.common.UserType;
import com.tablereservation.secret.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.tablereservation.manager.excepition.ErrorCode.LOGIN_CHECK_FAIL;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final ManagerService managerService;
    private final JwtAuthenticationProvider provider;

    public String managerLoginToken(ManagerSignInForm form) {
        Manager m = managerService.findValidManager(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));

        return provider.createToken(m.getEmail(), m.getManager_id(), UserType.MANAGER);
    }

}