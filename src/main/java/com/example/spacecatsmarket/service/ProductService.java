package com.example.spacecatsmarket.service;

import io.swagger.v3.oas.annotations.Parameter;
import com.example.spacecatsmarket.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(UUID id);

    ProductDTO updateProduct(@Parameter UUID id, @Parameter ProductDTO productDTO);

    void deleteProduct(UUID id);
}