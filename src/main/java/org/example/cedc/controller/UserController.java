package org.example.cedc.controller;

import org.example.cedc.model.dto.request.StoreUserRequestDTO;
import org.example.cedc.model.dto.response.StoreUserResponseDTO;
import org.example.cedc.model.entity.StoreUser;
import org.example.cedc.service.StoreUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private StoreUserService storeUserService;

    @PostMapping("/create")
    public ResponseEntity<StoreUserResponseDTO> createUser(@RequestBody StoreUserRequestDTO storeUserRequestDTO) {
        StoreUser storeUser = storeUserService.createUser(storeUserRequestDTO);
        return ResponseEntity.ok(new StoreUserResponseDTO(storeUser));
    }
}
