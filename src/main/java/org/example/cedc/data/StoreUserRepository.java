package org.example.cedc.data;

import org.example.cedc.model.entity.StoreUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

public interface StoreUserRepository extends JpaRepository<StoreUser, Long> {
    StoreUser findByUsername(String username);
    StoreUser findByEmail(String email);
}
