package org.example.cedc.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cedc.model.dto.OrderItemDTO;
import org.example.cedc.model.enums.StoreUserRole;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderBillRequestDTO {
    @NotEmpty
    private String currency;
    @NotEmpty
    private String targetCurrency;
    @NotEmpty
    private List<OrderItemDTO> orderItems;
    private String userEmail;
    private BigDecimal totalAmount;
    private StoreUserRole role;
    private Integer customerTenure;

}
