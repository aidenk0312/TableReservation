package com.tablereservation.customer.domain.model;

import com.tablereservation.customer.domain.ReservationForm;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservation_id;

    @Column(nullable = false)
    private Long store_id;

    @Column(nullable = false)
    private String customer_phone;

    @Column(nullable = false)
    private LocalDateTime reservation_time;

    @Column(nullable = false)
    private String reservation_status;

    public static Reservation from(ReservationForm form) {
        return Reservation.builder()
                .store_id(form.getStore_id())
                .customer_phone(form.getCustomer_phone())
                .reservation_time(form.getReservation_time())
                .reservation_status(form.getReservation_status())
                .build();
    }
}
