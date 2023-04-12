package com.tablereservation.manager.application;

import com.tablereservation.manager.dto.StoreDto;
import com.tablereservation.manager.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreApplication {
    private final StoreService storeService;

    public ResponseEntity<String> createStore(StoreDto storeDto, Long managerId) {
        storeService.createStore(storeDto, managerId);
        return new ResponseEntity<>("Store 생성이 완료 되었습니다.", HttpStatus.CREATED);
    }
}