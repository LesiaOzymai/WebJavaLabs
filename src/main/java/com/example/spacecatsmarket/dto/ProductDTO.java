package com.example.spacecatsmarket.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import com.example.spacecatsmarket.validation.CosmicWordCheck;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class ProductDTO {
    UUID id;

    @NotNull(message = "Product name cannot be null")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    @CosmicWordCheck(message = "Product name must contain a cosmic term like 'star', 'galaxy', or 'comet'")
    String name;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    double price;

    @NotNull(message = "Category ID cannot be null")
    UUID categoryId;

    String description;
}
