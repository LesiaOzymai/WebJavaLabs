package com.example.spacecatsmarket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.spacecatsmarket.dto.ProductDTO;
import com.example.spacecatsmarket.service.implementation.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    private ProductServiceImpl productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateProduct() throws Exception {
        ProductDTO productDTO = ProductDTO.builder().name("Test Product Star").price(100.0).build();
        ProductDTO createdProduct = ProductDTO.builder().id(UUID.randomUUID()).name("Test Product Comet").price(1523.0).build();

        when(productService.createProduct(any(ProductDTO.class))).thenReturn(createdProduct);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(createdProduct)));

        verify(productService, times(1)).createProduct(any(ProductDTO.class));
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<ProductDTO> productList = new ArrayList<>();
        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productList)));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById() throws Exception {
        UUID productId = UUID.randomUUID();
        ProductDTO product = ProductDTO.builder().id(productId).name("Test Product").build();

        when(productService.getProductById(any(UUID.class))).thenReturn(product);

        mockMvc.perform(get("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(product)));

        verify(productService, times(1)).getProductById(any(UUID.class));
    }

    @Test
    void testUpdateProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        ProductDTO productDTO = ProductDTO.builder().name("Test Product Star").price(100.0).build();
        ProductDTO updatedProduct = ProductDTO.builder().id(productId).name("Test Product Comet").price(1902.0).build();

        when(productService.updateProduct(any(UUID.class), any(ProductDTO.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedProduct)));

        verify(productService, times(1)).updateProduct(any(UUID.class), any(ProductDTO.class));
    }


    @Test
    void testDeleteProduct() throws Exception {
        UUID productId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(any(UUID.class));
    }
}

