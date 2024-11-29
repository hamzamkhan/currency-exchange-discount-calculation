package org.example.cedc;

import lombok.SneakyThrows;
import org.example.cedc.model.dto.OrderItemDTO;
import org.example.cedc.model.dto.request.OrderBillRequestDTO;
import org.example.cedc.model.dto.response.OrderBillResponseDTO;
import org.example.cedc.service.OrderBillService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class OrderBillServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderBillService orderBillService;

    @Test
    @SneakyThrows
    public void calculateOrder_success() {
        OrderBillRequestDTO requestDTO = createOrderBillRequestDTO();
        OrderBillResponseDTO responseDTO = orderBillService.calculateOrder(requestDTO);
        Assert.assertEquals(new BigDecimal("8.814000"), responseDTO.getPayableAmount());
    }

    private OrderBillRequestDTO createOrderBillRequestDTO() {
        OrderBillRequestDTO requestDTO = new OrderBillRequestDTO();
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setItemId("1");
        orderItemDTO.setQuantity(2);

        requestDTO.setOrderItems(Collections.singletonList(orderItemDTO));
        requestDTO.setCurrency("USD");
        requestDTO.setTargetCurrency("AED");
        requestDTO.setUserEmail("hamza@khan.com");

        return requestDTO;
    }



}
