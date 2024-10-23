package com.example.spacecatsmarket.domain;

import com.example.spacecatsmarket.common.ProductType;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Order {

    List<ProductType> productTypes;
    Double price;
    Long consumerReference;


}
