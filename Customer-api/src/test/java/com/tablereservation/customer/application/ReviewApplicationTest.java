package com.tablereservation.customer.application;

import com.tablereservation.customer.domain.ReviewDto;
import com.tablereservation.customer.domain.model.Reservation;
import com.tablereservation.customer.domain.model.Review;
import com.tablereservation.customer.service.ReservationService;
import com.tablereservation.customer.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewApplicationTest {
    @Mock
    private ReviewService reviewService;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReviewApplication reviewApplication;

    private Reservation reservation;
    private Review review;

    @BeforeEach
    void setUp() {
        reservation = new Reservation();
        reservation.setReservation_id(1L);
        reservation.setReservation_status("체크인 완료");

        review = new Review();
        review.setReview_id(1L);
        review.setReservation(reservation);
        review.setComment("맛집!");
        review.setRating(5);
    }

    @Test
    @DisplayName("리뷰 생성 Test")
    void createReviewSuccess() {
        when(reservationService.getReservationById(1L)).thenReturn(reservation);
        when(reviewService.createReview(any(Review.class))).thenReturn(review);

        Long reviewId = reviewApplication.createReview(1L, "맛집!", 5);

        assertEquals(1L, reviewId);
    }

    @Test
    @DisplayName("리뷰 조회 Test")
    void getReviewSuccess() {
        when(reviewService.getReviewById(1L)).thenReturn(review);

        ReviewDto fetchedReview = reviewApplication.getReviewDto(1L);

        assertEquals(1L, fetchedReview.getReview_id());
        assertEquals("맛집!", fetchedReview.getComment());
        assertEquals(5, fetchedReview.getRating());
    }

    @Test
    @DisplayName("리뷰 조회 실패 Test")
    void getReviewNotFound() {
        when(reviewService.getReviewById(2L)).thenThrow(new NoSuchElementException("리뷰를 찾을 수 없습니다. 리뷰 ID: 2"));

        assertThrows(NoSuchElementException.class, () -> reviewApplication.getReviewDto(2L));
    }

    @Test
    @DisplayName("리뷰 수정 Test")
    void updateReviewSuccess() {
        when(reviewService.getReviewById(1L)).thenReturn(review);
        when(reviewService.updateReview(review)).thenReturn(review);

        reviewApplication.updateReview(1L, "좋아요!", 4);

        assertEquals("좋아요!", review.getComment());
        assertEquals(4, review.getRating());

        verify(reviewService).updateReview(review);
    }

    @Test
    @DisplayName("리뷰 삭제 Test")
    void deleteReviewSuccess() {
        doNothing().when(reviewService).deleteReview(1L);

        reviewApplication.deleteReview(1L);

        verify(reviewService).deleteReview(1L);
    }

    @Test
    @DisplayName("특정 매장의 리뷰 목록 조회 Test")
    void getReviewsByStoreIdSuccess() {
        Review review2 = new Review();
        review2.setReview_id(2L);
        review2.setReservation(reservation);
        review2.setComment("아주 좋아요!");
        review2.setRating(4);

        List<Review> reviews = Arrays.asList(review, review2);
        PageImpl<Review> reviewPage = new PageImpl<>(reviews, PageRequest.of(0, 10, Sort.unsorted()), reviews.size());

        when(reviewService.getReviewsByStoreId(1L, 0, 10, "rating")).thenReturn(reviews);

        List<ReviewDto> fetchedReviews = reviewApplication.getReviewsByStoreId(1L, 0, 10, "rating");

        assertEquals(2, fetchedReviews.size());
        assertEquals(1L, fetchedReviews.get(0).getReview_id());
        assertEquals("맛집!", fetchedReviews.get(0).getComment());
        assertEquals(5, fetchedReviews.get(0).getRating());
        assertEquals(2L, fetchedReviews.get(1).getReview_id());
        assertEquals("아주 좋아요!", fetchedReviews.get(1).getComment());
        assertEquals(4, fetchedReviews.get(1).getRating());
    }
}
