package org.example.cedc.service;

import lombok.SneakyThrows;
import org.example.cedc.exception.ServiceLayerException;
import org.example.cedc.model.dto.OrderItemDTO;
import org.example.cedc.model.dto.request.OrderBillRequestDTO;
import org.example.cedc.model.dto.response.OrderBillResponseDTO;
import org.example.cedc.model.enums.ItemCategory;
import org.example.cedc.model.enums.StoreUserRole;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
class OrderBillServiceTest {
    @Autowired
    private OrderBillService orderBillService;

    @Test
    @SneakyThrows
    void calculateOrder_success_groceries() {
        OrderBillRequestDTO requestDTO = createOrderBillRequestDTO();
        OrderBillResponseDTO responseDTO = orderBillService.calculateOrder(requestDTO);
        Assert.assertEquals(new BigDecimal("8.82"), responseDTO.getPayableAmount());
    }

    @Test
    @SneakyThrows
    void calculateOrder_success_shoes_different_customer() {
        OrderBillRequestDTO requestDTO = createOrderBillRequestDTO();
        requestDTO.getOrderItems().get(0).setItemId("15");
        OrderBillResponseDTO responseDTO = orderBillService.calculateOrder(requestDTO);
        Assert.assertEquals(new BigDecimal("532.52"), responseDTO.getPayableAmount());
    }

    @Test
    @SneakyThrows
    void calculateOrder_success_shoes() {
        OrderBillRequestDTO requestDTO = createOrderBillRequestDTO();
        requestDTO.getOrderItems().get(0).setItemId("15");
        requestDTO.setUserEmail("hamza1@khan.com");
        OrderBillResponseDTO responseDTO = orderBillService.calculateOrder(requestDTO);
        Assert.assertEquals(new BigDecimal("367.25"), responseDTO.getPayableAmount());
    }

    @Test
    @SneakyThrows
    void calculateOrder_success_shoes_affiliate() {
        OrderBillRequestDTO requestDTO = createOrderBillRequestDTO();
        requestDTO.getOrderItems().get(0).setItemId("15");
        requestDTO.setUserEmail("hamza2@khan.com");
        OrderBillResponseDTO responseDTO = orderBillService.calculateOrder(requestDTO);
        Assert.assertEquals(new BigDecimal("477.43"), responseDTO.getPayableAmount());
    }

    @Test
    @SneakyThrows
    void calculateOrder_failure_item() {
        OrderBillRequestDTO requestDTO = createOrderBillRequestDTO();
        requestDTO.getOrderItems().get(0).setItemId("55");
        Assertions.assertThrows(ServiceLayerException.class, () -> orderBillService.calculateOrder(requestDTO));
    }

    @Test
    @SneakyThrows
    void calculateOrder_failure_user() {
        OrderBillRequestDTO requestDTO = createOrderBillRequestDTO();
        requestDTO.setUserEmail("test@gmail.com");
        Assertions.assertThrows(ServiceLayerException.class, () -> orderBillService.calculateOrder(requestDTO));
    }

    @Test
    @SneakyThrows
    void calculateOrder_success_withDetails() {
        OrderBillRequestDTO requestDTO = createOrderBillRequestWithDetails();
        OrderBillResponseDTO responseDTO = orderBillService.calculateOrder(requestDTO);
        Assert.assertEquals(new BigDecimal("1827.07"), responseDTO.getPayableAmount());
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


    private OrderBillRequestDTO createOrderBillRequestWithDetails() {
        OrderBillRequestDTO requestDTO = new OrderBillRequestDTO();
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setQuantity(2);
        orderItemDTO.setCategory(ItemCategory.ELECTRONICS);
        orderItemDTO.setName("Test Item");

        requestDTO.setOrderItems(Collections.singletonList(orderItemDTO));
        requestDTO.setUserEmail("hamza@khan.com");
        requestDTO.setCurrency("USD");
        requestDTO.setTargetCurrency("AED");
        requestDTO.setRole(StoreUserRole.CUSTOMER);
        requestDTO.setCustomerTenure(3);
        requestDTO.setTotalAmount(new BigDecimal(550));

        return requestDTO;
    }



}
