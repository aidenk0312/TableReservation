package com.tablereservation.customer.controller;

import com.tablereservation.customer.application.ReviewApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/customer/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewApplication reviewApplication;

    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody Map<String, Object> payload) {
        Long reservationId = Long.parseLong(payload.get("reservation_id").toString());
        String comment = payload.get("comment").toString();
        Integer rating = Integer.parseInt(payload.get("rating").toString());

        Long reviewId = reviewApplication.createReview(reservationId, comment, rating);
        return ResponseEntity.ok("리뷰가 성공적으로 생성되었습니다. 리뷰 ID: " + reviewId);
    }
}
