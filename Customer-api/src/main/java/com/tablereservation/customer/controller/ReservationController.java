package com.tablereservation.customer.controller;

import com.tablereservation.customer.application.ReservationApplication;
import com.tablereservation.customer.domain.ReservationForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationApplication reservationApplication;

    @PostMapping
    public ResponseEntity<String> createReservation(@RequestBody ReservationForm form) {
        Long reservationId = reservationApplication.createReservation(form);
        return ResponseEntity.ok("예약이 성공적으로 생성되었습니다. 예약 ID: " + reservationId);
    }
}
