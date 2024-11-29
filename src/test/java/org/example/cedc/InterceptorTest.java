package org.example.cedc;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class InterceptorTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void getHelloWorld_success() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/").with(httpBasic("hamza@khan.com", "ecxvawA$Tu")))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    public void getHelloWorld_fail() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/").with(httpBasic("sad@gmail.com", "aca")))
                .andExpect(status().isUnauthorized());
    }
}
