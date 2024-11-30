package org.example.cedc.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.cedc.data.StoreUserRepository;
import org.example.cedc.model.entity.StoreUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@Component
public class APIInterceptor implements HandlerInterceptor {
    private final StoreUserRepository storeUserRepository;

    public APIInterceptor(StoreUserRepository storeUserRepository) {
        this.storeUserRepository = storeUserRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Basic ")) {

            String base64Credentials = authHeader.substring("Basic ".length());
            byte[] decodedCredentials = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(decodedCredentials, StandardCharsets.UTF_8);

            String[] parts = credentials.split(":");
            String username = parts[0];
            String password = parts[1];

            // Authenticate user
            if (username == null || password == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            StoreUser user = storeUserRepository.findByEmail(username);
            if (user == null || !new BCryptPasswordEncoder().matches(password, user.getPassword())) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }
}
