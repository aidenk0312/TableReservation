package com.tablereservation.customer.controller;

import com.tablereservation.customer.application.ReservationApplication;
import com.tablereservation.customer.domain.ReservationForm;
import com.tablereservation.customer.domain.model.Reservation;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationApplication reservationApplication;

    @PostMapping
    @ApiOperation("예약 생성 API")
    public ResponseEntity<String> createReservation(@RequestBody ReservationForm form) {
        Long reservationId = reservationApplication.createReservation(form);
        return ResponseEntity.ok("예약이 성공적으로 생성되었습니다. 예약 ID: " + reservationId);
    }

    @GetMapping("/{reservationId}")
    @ApiOperation("예약 정보 조회 API")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long reservationId) {
        Reservation reservation = reservationApplication.getReservationById(reservationId);
        return ResponseEntity.ok(reservation);
    }

    @PutMapping("/{reservationId}")
    @ApiOperation("예약 수정 API")
    public ResponseEntity<String> updateReservation(@PathVariable Long reservationId, @RequestBody ReservationForm form) {
        Reservation updatedReservation = reservationApplication.updateReservation(reservationId, form);
        return ResponseEntity.ok("예약이 성공적으로 수정되었습니다. 예약 ID: " + updatedReservation.getReservation_id());
    }

    @DeleteMapping("/{reservationId}")
    @ApiOperation("예약 취소 API")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId) {
        reservationApplication.cancelReservation(reservationId);
        return ResponseEntity.ok("예약이 성공적으로 취소되었습니다. 예약 ID: " + reservationId);
    }

    @PostMapping("/{reservationId}/check-in")
    @ApiOperation("Kiosk 체크인 API")
    public ResponseEntity<String> checkIn(@PathVariable Long reservationId) {
        reservationApplication.checkIn(reservationId);
        return ResponseEntity.ok("체크인이 완료되었습니다. 예약 ID: " + reservationId);
    }

    @DeleteMapping("/{reservationId}/cancel-no-check-in")
    @ApiOperation("Kiosk 10분 지체 예약 취소 API")
    public ResponseEntity<String> cancelReservationWhenNoCheckInFor10Minutes(@PathVariable Long reservationId) {
        reservationApplication.cancelReservationWhenNoCheckInFor10Minutes(reservationId);
        return ResponseEntity.ok("10분간 체크인을 하지 않아 예약이 취소 됐습니다. 24시간 동안 예약이 불가능 합니다. 예약 ID: " + reservationId);
    }
}