package com.ps.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void shouldCreateOrder() throws Exception {
        Order order = new Order();
        order.setCustomerEmail("test@example.com");
        order.setTotalAmount(new BigDecimal("99.99"));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value("NEW"))
                .andExpect(jsonPath("$.customerEmail").value("test@example.com"));
    }

    @Test
    void shouldReturnBadRequestWhenEmailMissing() throws Exception {
        Order order = new Order();
        order.setTotalAmount(new BigDecimal("50.00"));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetOrderById() throws Exception {
        Order order = new Order();
        order.setCustomerEmail("get@example.com");
        order.setTotalAmount(new BigDecimal("25.00"));

        String response = mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readValue(response, Order.class).getId();

        mockMvc.perform(get("/orders/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerEmail").value("get@example.com"));
    }

    @Test
    void shouldReturnNotFoundForUnknownOrder() throws Exception {
        mockMvc.perform(get("/orders/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllOrders() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCancelNewOrder() throws Exception {
        Order order = new Order();
        order.setCustomerEmail("cancel@example.com");
        order.setTotalAmount(new BigDecimal("10.00"));

        String response = mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readValue(response, Order.class).getId();

        mockMvc.perform(post("/orders/" + id + "/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }
}
