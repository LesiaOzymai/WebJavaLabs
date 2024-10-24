package com.example.spacecatsmarket.service.mapper;

import com.example.spacecatsmarket.domain.order.OrderContext;
import com.example.spacecatsmarket.domain.payment.Payment;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "consumerReference", source = "customerReference")
    @Mapping(target = "cartId", source = "cartId")
    @Mapping(target = "amount", source = "totalPrice")
    Payment toPayment(OrderContext orderContext);
}
