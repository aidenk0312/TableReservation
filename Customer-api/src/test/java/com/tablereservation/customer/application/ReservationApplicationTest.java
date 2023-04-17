package com.tablereservation.customer.application;

import com.tablereservation.customer.domain.ReservationForm;
import com.tablereservation.customer.domain.model.Reservation;
import com.tablereservation.customer.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReservationApplicationTest {

    @MockBean
    private ReservationService reservationService;

    private ReservationApplication reservationApplication;

    @BeforeEach
    public void setUp() {
        reservationApplication = new ReservationApplication(reservationService);
    }

    @Test
    @DisplayName("예약 생성 Test")
    public void createReservationTest() {
        ReservationForm form = ReservationForm.builder()
                .store_id(1L)
                .customer_phone("010-1234-5678")
                .reservation_time(LocalDateTime.now().plusDays(1))
                .reservation_status("예약됨")
                .build();

        Reservation reservation = Reservation.from(form);
        reservation.setReservation_id(1L);

        when(reservationService.createReservation(form)).thenReturn(reservation);

        Long reservationId = reservationApplication.createReservation(form);

        assertThat(reservationId).isEqualTo(1L);

        verify(reservationService, times(1)).createReservation(form);
    }

    @Test
    @DisplayName("예약 조회 Test")
    public void getReservationByIdTest() {
        Long reservationId = 1L;
        Reservation reservation = Reservation.builder()
                .reservation_id(reservationId)
                .store_id(1L)
                .customer_phone("010-1234-5678")
                .reservation_time(LocalDateTime.now().plusDays(1))
                .reservation_status("예약됨")
                .build();

        when(reservationService.getReservationById(reservationId)).thenReturn(reservation);

        Reservation foundReservation = reservationApplication.getReservationById(reservationId);

        assertThat(foundReservation).isEqualTo(reservation);
        assertThat(foundReservation.getReservation_id()).isEqualTo(reservationId);
        assertThat(foundReservation.getStore_id()).isEqualTo(reservation.getStore_id());
        assertThat(foundReservation.getCustomer_phone()).isEqualTo(reservation.getCustomer_phone());
        assertThat(foundReservation.getReservation_time()).isEqualTo(reservation.getReservation_time());
        assertThat(foundReservation.getReservation_status()).isEqualTo(reservation.getReservation_status());

        verify(reservationService, times(1)).getReservationById(reservationId);
    }
}
