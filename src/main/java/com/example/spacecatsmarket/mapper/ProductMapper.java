package com.example.spacecatsmarket.mapper;

import com.example.spacecatsmarket.dto.ProductDTO;
import com.example.spacecatsmarket.domain.Product;
import org.mapstruct.Mapper;



import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO productDTO);

    List<ProductDTO> toProductDTOList(List<Product> productList);
    List<Product> toProductEntityList(List<ProductDTO> productDTOList);

}
