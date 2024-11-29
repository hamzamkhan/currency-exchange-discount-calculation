package org.example.cedc.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cedc.model.enums.ItemCategory;

import java.math.BigDecimal;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */
@Getter
@Setter
@Entity(name = "tbl_items")
@NoArgsConstructor
public class Item extends BaseEntity{
    @Column
    private String name;

    @Column
    private String description;

    @Column
    private BigDecimal price;

    @Column
    private Integer quantity;

    @Column
    @Enumerated(EnumType.STRING)
    private ItemCategory category;
}
