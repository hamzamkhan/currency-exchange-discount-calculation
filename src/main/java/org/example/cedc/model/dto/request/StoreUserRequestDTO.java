package org.example.cedc.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.example.cedc.model.enums.StoreUserRole;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@Getter
@Setter
public class StoreUserRequestDTO {
    private String name;
    private String email;
    private StoreUserRole role;
}
