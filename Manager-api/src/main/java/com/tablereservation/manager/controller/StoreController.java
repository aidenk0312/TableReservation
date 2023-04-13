package com.tablereservation.manager.controller;

import com.tablereservation.manager.application.StoreApplication;
import com.tablereservation.manager.dto.StoreDto;
import com.tablereservation.manager.service.StoreService;
import com.tablereservation.secret.config.JwtAuthenticationProvider;
import com.tablereservation.secret.common.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreApplication storeApplication;
    private final StoreService storeService;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @PostMapping("/create")
    public ResponseEntity<?> createStore(@RequestBody StoreDto storeDto, @RequestHeader("Authorization") String token) {
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token.substring(7));
        Long managerId = userVo.getId();

        return storeService.createStore(storeDto, managerId);
    }

    @PutMapping("/{storeId}")
    public ResponseEntity<String> updateStore(@PathVariable Long storeId, @RequestBody StoreDto storeDto) {
        storeApplication.updateStore(storeId, storeDto);
        return new ResponseEntity<>("Store 수정이 완료 되었습니다.", HttpStatus.OK);
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<String> deleteStore(@PathVariable Long storeId) {
        return storeApplication.deleteStore(storeId);
    }
}