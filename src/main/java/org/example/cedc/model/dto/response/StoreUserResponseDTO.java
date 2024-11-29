package org.example.cedc.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cedc.model.entity.StoreUser;
import org.example.cedc.model.enums.StoreUserRole;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreUserResponseDTO {
    private String email;
    private String username;
    private String password;
    private StoreUserRole role;

    public StoreUserResponseDTO(StoreUser storeUser) {
        this.email = storeUser.getEmail();
        this.username = storeUser.getUsername();
        this.password = storeUser.getPassword();
        this.role = storeUser.getRole();
    }
}
