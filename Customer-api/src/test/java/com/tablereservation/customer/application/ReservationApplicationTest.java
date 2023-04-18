package com.tablereservation.customer.application;

import com.tablereservation.customer.domain.ReservationForm;
import com.tablereservation.customer.domain.model.Kiosk;
import com.tablereservation.customer.domain.model.Reservation;
import com.tablereservation.customer.domain.repository.KioskRepository;
import com.tablereservation.customer.domain.repository.ReservationRepository;
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

    @MockBean
    private KioskRepository kioskRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    private ReservationApplication reservationApplication;

    @BeforeEach
    public void setUp() {
        reservationApplication = new ReservationApplication(reservationService, kioskRepository, reservationRepository);
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

    @Test
    @DisplayName("예약 수정 Test")
    public void updateReservationTest() {
        Long reservationId = 1L;
        ReservationForm form = ReservationForm.builder()
                .store_id(2L)
                .customer_phone("010-4321-8765")
                .reservation_time(LocalDateTime.now().plusDays(3))
                .reservation_status("예약 변경됨")
                .build();

        Reservation existingReservation = Reservation.builder()
                .reservation_id(reservationId)
                .store_id(1L)
                .customer_phone("010-1234-5678")
                .reservation_time(LocalDateTime.now().plusDays(1))
                .reservation_status("예약됨")
                .build();

        Reservation updatedReservation = Reservation.builder()
                .reservation_id(reservationId)
                .store_id(form.getStore_id())
                .customer_phone(form.getCustomer_phone())
                .reservation_time(form.getReservation_time())
                .reservation_status(form.getReservation_status())
                .build();

        when(reservationService.getReservationById(reservationId)).thenReturn(existingReservation);
        when(reservationService.updateReservation(reservationId, form)).thenReturn(updatedReservation);

        Reservation result = reservationApplication.updateReservation(reservationId, form);

        assertThat(result).isEqualTo(updatedReservation);
        assertThat(result.getReservation_id()).isEqualTo(updatedReservation.getReservation_id());
        assertThat(result.getStore_id()).isEqualTo(updatedReservation.getStore_id());
        assertThat(result.getCustomer_phone()).isEqualTo(updatedReservation.getCustomer_phone());
        assertThat(result.getReservation_time()).isEqualTo(updatedReservation.getReservation_time());
        assertThat(result.getReservation_status()).isEqualTo(updatedReservation.getReservation_status());

        verify(reservationService, times(1)).getReservationById(reservationId);
        verify(reservationService, times(1)).updateReservation(reservationId, form);
    }

    @Test
    @DisplayName("예약 취소 Test")
    public void cancelReservationTest() {
        Long reservationId = 1L;
        Reservation reservation = Reservation.builder()
                .reservation_id(reservationId)
                .store_id(1L)
                .customer_phone("010-1234-5678")
                .reservation_time(LocalDateTime.now().plusDays(1))
                .reservation_status("예약됨")
                .build();

        Reservation cancelledReservation = Reservation.builder()
                .reservation_id(reservationId)
                .store_id(1L)
                .customer_phone("010-1234-5678")
                .reservation_time(LocalDateTime.now().plusDays(1))
                .reservation_status("CANCELLED")
                .build();

        when(reservationService.getReservationById(reservationId)).thenReturn(reservation);
        doAnswer(invocation -> {
            reservation.setReservation_status("CANCELLED");
            return null;
        }).when(reservationService).cancelReservation(reservationId);

        reservationApplication.cancelReservation(reservationId);

        assertThat(reservation.getReservation_status()).isEqualTo(cancelledReservation.getReservation_status());

        verify(reservationService, times(1)).getReservationById(reservationId);
        verify(reservationService, times(1)).cancelReservation(reservationId);
    }

    @Test
    @DisplayName("체크인 테스트")
    public void checkInTest() {
        Long reservationId = 1L;
        Reservation reservation = Reservation.builder()
                .reservation_id(reservationId)
                .store_id(1L)
                .customer_phone("010-1234-5678")
                .reservation_time(LocalDateTime.now().plusDays(1))
                .reservation_status("예약됨")
                .build();

        Kiosk kiosk = Kiosk.builder().kiosk_id(1L).reservation(reservation).build();

        when(reservationService.getReservationById(reservationId)).thenReturn(reservation);
        when(kioskRepository.save(any(Kiosk.class))).thenReturn(kiosk);

        reservationApplication.checkIn(reservationId);

        verify(reservationService, times(1)).getReservationById(reservationId);
        verify(kioskRepository, times(1)).save(any(Kiosk.class));
    }

    @Test
    @DisplayName("10분간 체크인이 없을 시 예약 취소 Test")
    public void cancelReservationWhenNoCheckInFor10MinutesTest() {
        Long reservationId = 1L;
        Reservation reservation = Reservation.builder()
                .reservation_id(reservationId)
                .store_id(1L)
                .customer_phone("010-1234-5678")
                .reservation_time(LocalDateTime.now().minusMinutes(11))
                .reservation_status("예약됨")
                .build();

        when(reservationService.getReservationById(reservationId)).thenReturn(reservation);
        when(kioskRepository.findByReservationId(reservationId)).thenReturn(null);
        doAnswer(invocation -> {
            reservation.setReservation_status("CANCELLED");
            reservation.setReservation_time(LocalDateTime.now().plusDays(1));
            return null;
        }).when(reservationService).cancelReservation(reservationId);

        reservationApplication.cancelReservationWhenNoCheckInFor10Minutes(reservationId);

        assertThat(reservation.getReservation_status()).isEqualTo("CANCELLED");

        verify(reservationService, times(1)).getReservationById(reservationId);
        verify(kioskRepository, times(1)).findByReservationId(reservationId);
        verify(reservationService, times(1)).cancelReservation(reservationId);
    }
}