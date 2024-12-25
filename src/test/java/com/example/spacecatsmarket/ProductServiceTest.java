package com.example.spacecatsmarket;

import com.example.spacecatsmarket.dto.ProductDTO;
import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.mapper.ProductMapper;
import com.example.spacecatsmarket.service.implementation.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductDTO productDTO;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productDTO = ProductDTO.builder().name("Test Product").price(100.0).description("Test Description").categoryId(1L).build();

        product = Product.builder().id(1L).name("Test Product").price(100.0).description("Test Description").categoryId(1L).build();

        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productDTO);
    }

    @Test
    void testCreateProduct() {
        ProductDTO createdProduct = productService.createProduct(productDTO);

        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getName());
        verify(productMapper).toEntity(productDTO);
        verify(productMapper).toDTO(product);
    }

    @Test
    void testGetAllProducts() {
        when(productMapper.toProductDTOList(anyList())).thenReturn(List.of(productDTO));

        List<ProductDTO> products = productService.getAllProducts();

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getName());
        verify(productMapper).toProductDTOList(anyList());
    }

    @Test
    void testGetProductById() {
        productService.createProduct(productDTO);

        ProductDTO foundProduct = productService.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals("Test Product", foundProduct.getName());

        verify(productMapper, times(2)).toDTO(any(Product.class));
    }

    @Test
    void testUpdateProduct() {
        productService.createProduct(productDTO);

        productDTO = productDTO.toBuilder().name("Updated Product").price(150.0).build();

        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        ProductDTO updatedProduct = productService.updateProduct(1L, productDTO);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals(150.0, updatedProduct.getPrice());

        verify(productMapper, times(2)).toDTO(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        productService.createProduct(productDTO);

        assertDoesNotThrow(() -> productService.deleteProduct(1L));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.getProductById(1L));
        assertEquals("Product not found", exception.getMessage());
    }
}
