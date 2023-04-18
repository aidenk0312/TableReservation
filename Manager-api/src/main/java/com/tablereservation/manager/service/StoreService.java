package com.tablereservation.manager.service;

import com.tablereservation.manager.domain.model.Manager;
import com.tablereservation.manager.domain.model.Store;
import com.tablereservation.manager.domain.repository.ManagerRepository;
import com.tablereservation.manager.domain.repository.StoreRepository;
import com.tablereservation.manager.dto.StoreDto;
import com.tablereservation.manager.excepition.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

import static com.tablereservation.manager.excepition.ErrorCode.NOT_FOUND_MANAGER;
import static com.tablereservation.manager.excepition.ErrorCode.NOT_FOUND_STORE;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final ManagerRepository managerRepository;

    public ResponseEntity<?> createStore(StoreDto storeDto, Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_MANAGER));

        Store store = Store.builder()
                .manager(manager)
                .storeName(storeDto.getStore_name())
                .store_phone(storeDto.getStore_phone())
                .address(storeDto.getAddress())
                .build();
        storeRepository.save(store);

        return ResponseEntity.ok().build();
    }

    public void updateStore(Long storeId, StoreDto storeDto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_STORE));

        store.setStoreName(storeDto.getStore_name());
        store.setStore_phone(storeDto.getStore_phone());
        store.setAddress(storeDto.getAddress());

        storeRepository.save(store);
    }

    public void deleteStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_STORE));

        storeRepository.delete(store);
    }

    public Page<Store> searchStores(String searchKeyword, Pageable pageable) {
        return storeRepository.findByStoreNameContaining(searchKeyword, pageable);
    }

    public Optional<Store> getStore(Long storeId) {
        return storeRepository.findById(storeId);
    }
}