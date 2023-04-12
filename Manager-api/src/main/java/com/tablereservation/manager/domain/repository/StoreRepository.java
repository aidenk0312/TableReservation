package com.tablereservation.manager.domain.repository;

import com.tablereservation.manager.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
