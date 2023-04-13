package com.tablereservation.manager.domain.repository;

import com.tablereservation.manager.domain.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Page<Store> findByStoreNameContaining(String searchKeyword, Pageable pageable);
}