package org.example.cedc.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cedc.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@Getter
@Setter
@Entity(name = "tbl_order")
@NoArgsConstructor
public class Order extends BaseEntity {

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "original_currency")
    private String originalCurrency;

    @Column(name = "target_currency")
    private String targetCurrency;

    @Column(name = "org_curr_amount")
    private BigDecimal totalAmountOriginalCurrency;

    @Column
    private BigDecimal discount; // Discount in percentage or fixed amount

    @Column(name = "target_payable_amount")
    private BigDecimal totalPayableAmountTargetCurrency;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private StoreUser storeUser;
}
