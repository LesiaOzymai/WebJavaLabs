package com.example.spacecatsmarket.service.implementation;

import com.example.spacecatsmarket.dto.ProductDTO;
import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.mapper.ProductMapper;
import com.example.spacecatsmarket.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final Map<UUID, Product> mockProductDatabase = new HashMap<>();


    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        UUID newId = UUID.randomUUID();
        Product product = productMapper.toEntity(productDTO).toBuilder().id(newId).build();
        mockProductDatabase.put(newId, product);
        return productMapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = new ArrayList<>(mockProductDatabase.values());
        return productMapper.toProductDTOList(products);
    }

    @Override
    public ProductDTO getProductById(UUID id) {
        Product product = mockProductDatabase.get(id);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        return productMapper.toDTO(product);
    }

    @Override
    public ProductDTO updateProduct(UUID id, ProductDTO productDTO) {
        Product existingProduct = mockProductDatabase.get(id);
        if (existingProduct == null) {
            throw new RuntimeException("Product not found");
        }
        Product updatedProduct = existingProduct.toBuilder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .categoryId(productDTO.getCategoryId())
                .build();
        mockProductDatabase.put(id, updatedProduct);
        return productMapper.toDTO(updatedProduct);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(UUID id) {
        mockProductDatabase.remove(id);
    }
}