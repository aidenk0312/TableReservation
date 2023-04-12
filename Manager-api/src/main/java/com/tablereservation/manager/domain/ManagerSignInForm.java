package com.tablereservation.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ManagerSignInForm {
    private String email;
    private String password;
}