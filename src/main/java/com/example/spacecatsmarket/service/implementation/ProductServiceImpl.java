package com.example.spacecatsmarket.service.implementation;

import com.example.spacecatsmarket.dto.ProductDTO;
import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.mapper.ProductMapper;
import com.example.spacecatsmarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final Map<Long, Product> mockProductDatabase = new HashMap<>();
    private Long productIdSequence = 1L;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO).toBuilder().id(productIdSequence++).build();
        mockProductDatabase.put(product.getId(), product);
        return productMapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = new ArrayList<>(mockProductDatabase.values());
        return productMapper.toProductDTOList(products);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = mockProductDatabase.get(id);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        return productMapper.toDTO(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = mockProductDatabase.get(id).toBuilder().name(productDTO.getName()).price(productDTO.getPrice())
                .description(productDTO.getDescription()).categoryId(productDTO.getCategoryId()).build();
        System.out.println("Product retrieved: " + product);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        mockProductDatabase.put(id, product);
        return productMapper.toDTO(product);
    }


    @Override
    public void deleteProduct(Long id) {
        if (!mockProductDatabase.containsKey(id)) {
            throw new RuntimeException("Product not found");
        }
        mockProductDatabase.remove(id);
    }


}
