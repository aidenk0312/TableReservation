package com.tablereservation.customer.domain.model;

import com.tablereservation.customer.domain.CustomerSignUpForm;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class Customer extends BaseEntity{

    @Id
    @Column(name = "customer_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_id;
    @Column(unique = true)
    private String email;
    private String password;
    private String phone;

    private LocalDateTime verifyExpiredAt;
    private String verificationCode;
    private boolean verify;

    @Column(columnDefinition = "int default 0")
    private Integer balance;

    public static Customer from(CustomerSignUpForm form) {
        return Customer.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(form.getPassword())
                .phone(form.getPhone())
                .build();
    }
}