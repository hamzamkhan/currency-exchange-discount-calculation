package org.example.cedc.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderBillResponseDTO {
    private BigDecimal payableAmount;
}
