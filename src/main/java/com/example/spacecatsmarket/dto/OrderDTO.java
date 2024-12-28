package com.example.spacecatsmarket.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Value;
import com.example.spacecatsmarket.domain.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class OrderDTO {
    UUID id;
    List<Product> products;
    @NotNull
    @Past
    LocalDateTime orderDate;
    String customerName;

}
