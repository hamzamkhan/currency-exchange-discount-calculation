package org.example.cedc.service;

import lombok.SneakyThrows;
import org.example.cedc.exception.ServiceLayerException;
import org.example.cedc.model.dto.request.StoreUserRequestDTO;
import org.example.cedc.model.entity.StoreUser;
import org.example.cedc.model.enums.StoreUserRole;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class StoreUserServiceTest {
    @Autowired
    private StoreUserService storeUserService;

    @Test
    @SneakyThrows
    void createUser_success() {
        StoreUserRequestDTO requestDTO = createDTO();
        StoreUser storeUser = storeUserService.createUser(requestDTO);
        Assert.assertEquals("Test User", storeUser.getUsername());
    }

    @Test
    @SneakyThrows
    void createUser_failure() {
        StoreUserRequestDTO requestDTO = createDTO();
        requestDTO.setEmail("hamza@khan.com");
        Assertions.assertThrows(ServiceLayerException.class, () -> storeUserService.createUser(requestDTO));
    }

    private StoreUserRequestDTO createDTO(){
        StoreUserRequestDTO requestDTO = new StoreUserRequestDTO();
        requestDTO.setEmail("testuser@example.com");
        requestDTO.setName("Test User");
        requestDTO.setRole(StoreUserRole.EMPLOYEE);

        return requestDTO;
    }
}
