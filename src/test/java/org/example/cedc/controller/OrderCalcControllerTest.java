package org.example.cedc.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class OrderCalcControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void calculateOrder_success() {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/calculate")
                        .with(httpBasic("hamza@khan.com", "ecxvawA$Tu"))
                .content("""
                            {
                                "currency": "USD",
                                "targetCurrency": "AED",
                                "orderItems": [
                                    {
                                        "itemId": 15,
                                        "quantity": 2
                                    }
                                ],
                                "userEmail": "hamza@khan.com"
                            }
                           """)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payableAmount").value("532.52"));
    }

    @Test
    @SneakyThrows
    void calculateOrder_withDetailsFromRequestBody() {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/calculate")
                        .with(httpBasic("hamza@khan.com", "ecxvawA$Tu"))
                        .content("""
                                    {
                                        "currency": "USD",
                                        "targetCurrency": "AED",
                                        "orderItems": [
                                            {
                                                "quantity": 2,
                                                "category": "GROCERIES",
                                                "name": "Test Item"
                                            }
                                        ],
                                        "userEmail": "hamza@khan.com",
                                        "role": "CUSTOMER",
                                        "customerTenure": 1,
                                        "totalAmount": 550
                                    }
                                    """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payableAmount").value("1928.07"));
    }
}
