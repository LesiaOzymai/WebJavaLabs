package com.example.spacecatsmarket.mapper;

import com.example.spacecatsmarket.dto.CategoryDTO;
import com.example.spacecatsmarket.domain.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDTO(Category category);

    Category toEntity(CategoryDTO categoryDTO);
}
