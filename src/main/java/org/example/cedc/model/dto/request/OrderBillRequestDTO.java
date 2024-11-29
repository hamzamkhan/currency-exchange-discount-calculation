package org.example.cedc.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cedc.model.dto.OrderItemDTO;

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
    private String currency;
    private String targetCurrency;
    private List<OrderItemDTO> orderItems;
    private String userEmail;
}
