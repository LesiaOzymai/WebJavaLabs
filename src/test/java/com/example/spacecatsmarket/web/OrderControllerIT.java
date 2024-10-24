package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.domain.payment.Payment;
import com.example.spacecatsmarket.service.CustomerService;
import com.example.spacecatsmarket.service.OrderService;
import com.example.spacecatsmarket.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureMockMvc
class OrderControllerIT {

    private final Long CUSTOMER_ID = 1L;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private PaymentService paymentService;

    @SpyBean
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        reset(paymentService);
    }

    @Test
    @SneakyThrows
    void shouldGetCustomerById(){

//        here you should save your entity to database -- thin integration tests
//        or you also can mock the response from the repository -- case unit tests


        mockMvc.perform(
            get("/api/v1/customers/{id}", CUSTOMER_ID))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Alice Johnson"));

//        verify(customerService).getCustomerDetailsById(CUSTOMER_ID);

    }

}
