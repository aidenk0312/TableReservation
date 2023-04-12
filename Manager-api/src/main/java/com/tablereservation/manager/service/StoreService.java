package com.tablereservation.manager.service;

import com.tablereservation.manager.domain.model.Manager;
import com.tablereservation.manager.domain.model.Store;
import com.tablereservation.manager.domain.repository.ManagerRepository;
import com.tablereservation.manager.domain.repository.StoreRepository;
import com.tablereservation.manager.dto.StoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final ManagerRepository managerRepository;

    public ResponseEntity<?> createStore(StoreDto storeDto, Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        Store store = Store.builder()
                .manager(manager)
                .store_name(storeDto.getStore_name())
                .store_phone(storeDto.getStore_phone())
                .address(storeDto.getAddress())
                .build();
        storeRepository.save(store);

        return ResponseEntity.ok().build();
    }
}