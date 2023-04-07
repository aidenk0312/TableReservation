package com.tablereservation.manager.domain.model;

import com.tablereservation.manager.domain.ManagerSignUpForm;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class Manager extends BaseEntity{

    @Id
    @Column(name = "manager_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long manager_id;
    @Column(unique = true)
    private String email;
    private String password;
    private String phone;

    private LocalDateTime verifyExpiredAt;
    private String verificationCode;
    private boolean verify;

    @Column(columnDefinition = "int default 0")
    private Integer balance;

    public static Manager from(ManagerSignUpForm form) {
        return Manager.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(form.getPassword())
                .phone(form.getPhone())
                .build();
    }
}