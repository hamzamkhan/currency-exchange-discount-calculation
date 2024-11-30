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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class StoreUserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void test_createUser_success() {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/create")
                        .content("""
                                {
                                    "name": "Hamza Mustafa Khan",
                                    "email": "hamza@gmail.com",
                                    "role": "CUSTOMER"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("hamza@gmail.com"));
    }
}
