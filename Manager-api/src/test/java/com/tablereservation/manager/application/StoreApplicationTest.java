package com.tablereservation.manager.application;

import com.tablereservation.manager.dto.StoreDto;
import com.tablereservation.manager.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StoreApplicationTest {

    @Mock
    private StoreService storeService;

    @InjectMocks
    private StoreApplication storeApplication;

    private StoreDto storeDto;
    private Long managerId;

    @BeforeEach
    public void setUp() {
        storeDto = new StoreDto();
        storeDto.setStore_name("test 가게");
        storeDto.setStore_phone("02-1234-5678");
        storeDto.setAddress("Test 주소");

        managerId = 1L;
    }

    @Test
    public void createStore_success() {
        doNothing().when(storeService).createStore(any(StoreDto.class), anyLong());

        ResponseEntity<String> response = storeApplication.createStore(storeDto, managerId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Store 생성이 완료 되었습니다.", response.getBody());
    }
}
