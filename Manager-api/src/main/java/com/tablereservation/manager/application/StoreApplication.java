package com.tablereservation.manager.application;

import com.tablereservation.manager.domain.model.Store;
import com.tablereservation.manager.domain.repository.StoreRepository;
import com.tablereservation.manager.dto.StoreDto;
import com.tablereservation.manager.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreApplication {
    private final StoreService storeService;
    private final StoreRepository storeRepository;

    public ResponseEntity<String> createStore(StoreDto storeDto, Long managerId) {
        storeService.createStore(storeDto, managerId);
        return new ResponseEntity<>("Store 생성이 완료 되었습니다.", HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateStore(Long storeId, StoreDto storeDto) {
        storeService.updateStore(storeId, storeDto);
        return ResponseEntity.ok("Store 수정이 완료 되었습니다.");
    }

    public ResponseEntity<String> deleteStore(Long storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.ok("Store 삭제가 완료 되었습니다.");
    }

    public Page<Store> searchStores(String searchKeyword, Pageable pageable) {
        return storeRepository.findByStoreNameContaining(searchKeyword, pageable);
    }

    public Optional<Store> getStore(Long storeId) {
        return storeService.getStore(storeId);
    }
}