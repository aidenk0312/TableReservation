package com.tablereservation.customer.domain.repository;

import com.tablereservation.customer.domain.model.Review;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "SELECT r.* FROM reviews r JOIN reservation res ON r.reservation_id = res.reservation_id JOIN store s ON res.store_id = s.store_id WHERE s.store_id = :storeId",
            countQuery = "SELECT COUNT(*) FROM reviews r JOIN reservation res ON r.reservation_id = res.reservation_id JOIN store s ON res.store_id = s.store_id WHERE s.store_id = :storeId",
            nativeQuery = true)
    Page<Review> findByReservation_StoreId(@Param("storeId") Long storeId, Pageable pageable);
}