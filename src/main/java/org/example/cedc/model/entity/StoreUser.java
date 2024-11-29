package org.example.cedc.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.example.cedc.model.enums.StoreUserRole;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */
@Getter
@Setter
@Entity(name = "tbl_store_user")
public class StoreUser extends BaseEntity {
    @Column(name = "name")
    private String username;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private StoreUserRole role;
}
