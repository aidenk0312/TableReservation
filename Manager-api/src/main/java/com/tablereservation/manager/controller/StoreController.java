package com.tablereservation.manager.controller;

import com.tablereservation.manager.application.StoreApplication;
import com.tablereservation.manager.domain.model.Store;
import com.tablereservation.manager.dto.StoreDto;
import com.tablereservation.manager.service.StoreService;
import com.tablereservation.secret.config.JwtAuthenticationProvider;
import com.tablereservation.secret.common.UserVo;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreApplication storeApplication;
    private final StoreService storeService;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @PostMapping("/create")
    @ApiOperation("가게 생성 API")
    public ResponseEntity<?> createStore(@RequestBody StoreDto storeDto, @RequestHeader("Authorization") String token) {
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token.substring(7));
        Long managerId = userVo.getId();

        return storeService.createStore(storeDto, managerId);
    }

    @PutMapping("/{storeId}")
    @ApiOperation("가게 정보 수정 API")
    public ResponseEntity<String> updateStore(@PathVariable Long storeId, @RequestBody StoreDto storeDto) {
        storeApplication.updateStore(storeId, storeDto);
        return new ResponseEntity<>("Store 수정이 완료 되었습니다.", HttpStatus.OK);
    }

    @DeleteMapping("/{storeId}")
    @ApiOperation("가게 삭제 API")
    public ResponseEntity<String> deleteStore(@PathVariable Long storeId) {
        return storeApplication.deleteStore(storeId);
    }

    @GetMapping("/search")
    @ApiOperation("가게 검색 API")
    public ResponseEntity<Page<Store>> searchStores(@RequestParam("searchKeyword") String searchKeyword, Pageable pageable) {
        return ResponseEntity.ok(storeApplication.searchStores(searchKeyword, pageable));
    }

    @GetMapping("/{storeId}")
    @ApiOperation("가게 정보 상세 정보 조회 API")
    public ResponseEntity<Store> getStore(@PathVariable Long storeId) {
        return storeApplication.getStore(storeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}