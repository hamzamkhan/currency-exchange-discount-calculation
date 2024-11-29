package org.example.cedc;

import lombok.SneakyThrows;
import org.example.cedc.exception.ServiceLayerException;
import org.example.cedc.model.dto.OrderItemDTO;
import org.example.cedc.model.dto.request.OrderBillRequestDTO;
import org.example.cedc.model.dto.response.OrderBillResponseDTO;
import org.example.cedc.service.OrderBillService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    private OrderBillService orderBillService;

    @Test
    @SneakyThrows
    public void calculateOrder_success_groceries() {
        OrderBillRequestDTO requestDTO = createOrderBillRequestDTO();
        OrderBillResponseDTO responseDTO = orderBillService.calculateOrder(requestDTO);
        Assert.assertEquals(new BigDecimal("8.82"), responseDTO.getPayableAmount());
    }

    @Test
    @SneakyThrows
    public void calculateOrder_success_shoes() {
        OrderBillRequestDTO requestDTO = createOrderBillRequestDTO();
        requestDTO.getOrderItems().get(0).setItemId("15");
        requestDTO.setUserEmail("hamza1@khan.com");
        OrderBillResponseDTO responseDTO = orderBillService.calculateOrder(requestDTO);
        Assert.assertEquals(new BigDecimal("367.25"), responseDTO.getPayableAmount());
    }

    @Test
    @SneakyThrows
    public void calculateOrder_success_shoes_affiliat() {
        OrderBillRequestDTO requestDTO = createOrderBillRequestDTO();
        requestDTO.getOrderItems().get(0).setItemId("15");
        requestDTO.setUserEmail("hamza2@khan.com");
        OrderBillResponseDTO responseDTO = orderBillService.calculateOrder(requestDTO);
        Assert.assertEquals(new BigDecimal("477.43"), responseDTO.getPayableAmount());
    }

    @Test
    @SneakyThrows
    public void calculateOrder_failure_item() {
        OrderBillRequestDTO requestDTO = createOrderBillRequestDTO();
        requestDTO.getOrderItems().get(0).setItemId("55");
        Assertions.assertThrows(ServiceLayerException.class, () -> orderBillService.calculateOrder(requestDTO));
    }

    @Test
    @SneakyThrows
    public void calculateOrder_failure_user() {
        OrderBillRequestDTO requestDTO = createOrderBillRequestDTO();
        requestDTO.setUserEmail("test@gmail.com");
        Assertions.assertThrows(ServiceLayerException.class, () -> orderBillService.calculateOrder(requestDTO));
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
