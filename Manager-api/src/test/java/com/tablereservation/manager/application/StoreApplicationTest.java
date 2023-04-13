package com.tablereservation.manager.application;

import com.tablereservation.manager.domain.repository.StoreRepository;
import com.tablereservation.manager.dto.StoreDto;
import com.tablereservation.manager.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tablereservation.manager.domain.model.Store;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StoreApplicationTest {

    @MockBean
    private StoreService storeService;

    @MockBean
    private StoreRepository storeRepository;

    @Autowired
    private StoreApplication storeApplication;

    private StoreDto storeDto;
    private Long managerId;
    private Long storeId;

    @BeforeEach
    public void setUp() {
        storeDto = new StoreDto();
        storeDto.setStore_name("test 가게");
        storeDto.setStore_phone("02-1234-5678");
        storeDto.setAddress("Test 주소");

        managerId = 1L;
        storeId = 2L;
    }

    @Test
    @DisplayName("가게 생성 Test")
    public void createStore_success() {
        lenient().doReturn(ResponseEntity.ok().build()).when(storeService).createStore(any(StoreDto.class), anyLong());

        ResponseEntity<String> response = storeApplication.createStore(storeDto, managerId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Store 생성이 완료 되었습니다.", response.getBody());
    }

    @Test
    @DisplayName("가게 정보 수정 Test")
    public void updateStore_success() {
        ResponseEntity<String> response = storeApplication.updateStore(storeId, storeDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Store 수정이 완료 되었습니다.", response.getBody());
    }

    @Test
    @DisplayName("가게 삭제 Test")
    public void deleteStore_success() {
        ResponseEntity<String> response = storeApplication.deleteStore(storeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Store 삭제가 완료 되었습니다.", response.getBody());
    }

    @Test
    @DisplayName("가게 검색 Test")
    public void searchStores_success() {
        List<Store> stores = Collections.singletonList(Store.builder()
                .store_id(storeId)
                .storeName(storeDto.getStore_name())
                .store_phone(storeDto.getStore_phone())
                .address(storeDto.getAddress())
                .build());
    }

    @Test
    @DisplayName("가게 정보 조회 Test")
    public void getStore_success() {
        Store store = Store.builder()
                .store_id(storeId)
                .storeName(storeDto.getStore_name())
                .store_phone(storeDto.getStore_phone())
                .address(storeDto.getAddress())
                .build();

        lenient().when(storeService.getStore(anyLong())).thenReturn(Optional.of(store));

        Optional<Store> foundStore = storeApplication.getStore(storeId);

        assertTrue(foundStore.isPresent());
        assertEquals(storeId, foundStore.get().getStore_id());
        assertEquals(storeDto.getStore_name(), foundStore.get().getStoreName());
        assertEquals(storeDto.getStore_phone(), foundStore.get().getStore_phone());
        assertEquals(storeDto.getAddress(), foundStore.get().getAddress());
    }
}