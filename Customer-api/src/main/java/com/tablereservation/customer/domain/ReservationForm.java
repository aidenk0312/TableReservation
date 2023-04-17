package com.tablereservation.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationForm {
    private Long store_id;
    private String customer_phone;
    private LocalDateTime reservation_time;
    private String reservation_status;
}
