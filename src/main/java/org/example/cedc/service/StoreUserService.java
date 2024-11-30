package org.example.cedc.service;

import org.example.cedc.data.StoreUserRepository;
import org.example.cedc.exception.ServiceLayerException;
import org.example.cedc.model.dto.request.StoreUserRequestDTO;
import org.example.cedc.model.entity.StoreUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@Service
public class StoreUserService {
    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$!";

    private final StoreUserRepository storeUserRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public StoreUserService(StoreUserRepository storeUserRepository) {
        this.storeUserRepository = storeUserRepository;
    }

    public StoreUser createUser(StoreUserRequestDTO storeUserRequestDTO) {
        StoreUser existingUser = storeUserRepository.findByEmail(storeUserRequestDTO.getEmail());
        if(Objects.nonNull(existingUser)){
            throw new ServiceLayerException("User already exists", HttpStatus.BAD_REQUEST);
        }

        String rawPassword = generateRandomPassword(10);
        String encodedPassword = passwordEncoder.encode(rawPassword);

        StoreUser user = new StoreUser();
        user.setUsername(storeUserRequestDTO.getName());
        user.setEmail(storeUserRequestDTO.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(storeUserRequestDTO.getRole());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        storeUserRepository.save(user);

        user.setPassword(rawPassword);
        return user;
    }

    private String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }
}
