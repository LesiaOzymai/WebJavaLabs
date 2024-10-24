package com.example.spacecatsmarket.domain.order;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Order {

    String id;
    List<OrderEntry> entries;
    String cartId;
    String consumerReference;
    Double totalPrice;


}
