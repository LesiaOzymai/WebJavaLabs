package com.example.spacecatsmarket.domain;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class Product {
    UUID id;
    String name;
    double price;
    UUID categoryId;
    String description;
}

