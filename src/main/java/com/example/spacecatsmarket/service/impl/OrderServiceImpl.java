package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.domain.order.Order;
import com.example.spacecatsmarket.domain.order.OrderContext;
import com.example.spacecatsmarket.domain.order.OrderEntry;
import com.example.spacecatsmarket.domain.payment.PaymentTransaction;
import com.example.spacecatsmarket.service.OrderService;
import com.example.spacecatsmarket.service.PaymentService;
import com.example.spacecatsmarket.service.exception.PaymentTransactionFailed;
import com.example.spacecatsmarket.service.mapper.PaymentMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.spacecatsmarket.common.PaymentStatus.FAILURE;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @Override
    public Order placeOrder(OrderContext orderContext) {
        PaymentTransaction paymentTransaction = paymentService.processPayment(paymentMapper.toPayment(orderContext));
        if(FAILURE.equals(paymentTransaction.getStatus())) {
           throw new PaymentTransactionFailed(paymentTransaction.getId(), orderContext.getCartId());
        }
        // TODO add mock for order service
        return buildOrder(orderContext.getCartId(), orderContext.getEntries(), orderContext.getTotalPrice(), orderContext.getCustomerReference());
    }

    private Order buildOrder(String cartId, List<OrderEntry> entries, Double totalPrice, String consumerReference) {
        return Order.builder()
            .id("order123")
            .cartId(cartId)
            .entries(entries)
            .totalPrice(totalPrice)
            .consumerReference(consumerReference)
            .build();
    }
}
