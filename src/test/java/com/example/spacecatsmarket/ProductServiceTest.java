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
import java.util.UUID;

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

        UUID productId = UUID.randomUUID();
        productDTO = ProductDTO.builder().id(productId).name("Test Product").price(100.0).description("Test Description").categoryId(UUID.randomUUID()).build();

        product = Product.builder().id(productId).name("Test Product").price(100.0).description("Test Description").categoryId(UUID.randomUUID()).build();

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
        UUID productId = productDTO.getId();
        productService.createProduct(productDTO);

        ProductDTO foundProduct = productService.getProductById(productId);

        assertNotNull(foundProduct);
        assertEquals("Test Product", foundProduct.getName());
        verify(productMapper, times(2)).toDTO(any(Product.class));
    }

    @Test
    void testUpdateProduct() {
        productService.createProduct(productDTO);

        productDTO = productDTO.toBuilder().name("Updated Product").price(150.0).build();

        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        ProductDTO updatedProduct = productService.updateProduct(productDTO.getId(), productDTO);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals(150.0, updatedProduct.getPrice());

        verify(productMapper, times(2)).toDTO(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        UUID productId = productDTO.getId();
        productService.createProduct(productDTO);

        assertDoesNotThrow(() -> productService.deleteProduct(productId));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.getProductById(productId));
        assertEquals("Product not found", exception.getMessage());
    }
}
