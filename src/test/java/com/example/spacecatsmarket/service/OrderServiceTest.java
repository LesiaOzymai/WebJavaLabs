package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.common.PaymentStatus;
import com.example.spacecatsmarket.common.ProductType;
import com.example.spacecatsmarket.config.MappersTestConfiguration;
import com.example.spacecatsmarket.domain.order.Order;
import com.example.spacecatsmarket.domain.order.OrderContext;
import com.example.spacecatsmarket.domain.order.OrderEntry;
import com.example.spacecatsmarket.domain.payment.Payment;
import com.example.spacecatsmarket.domain.payment.PaymentTransaction;
import com.example.spacecatsmarket.service.impl.OrderServiceImpl;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {OrderServiceImpl.class})
@ExtendWith(SpringExtension.class)
@Import(MappersTestConfiguration.class)
@DisplayName("Order Service Tests")
class OrderServiceTest {

    private static final OrderEntry DEFAULT_ORDER_ENTRY = buildOrderEntry();
    private static final OrderContext ORDER_CONTEXT = buildOrderContext();
    private static final String CART_ID = "cartId";
    private static final String CUSTOMER_REFERENCE = "customerRef";
    private static final double TOTAL_PRICE = 105.0;

    @MockBean
    private PaymentService paymentService;

    @Captor
    private ArgumentCaptor<Payment> paymentArgumentCaptor;

    @Autowired
    private OrderService orderService;

    @Test
    void shouldPlaceOrder() {

        when(paymentService.processPayment(paymentArgumentCaptor.capture())).thenAnswer(inv -> buildPaymentTransaction(((Payment) inv.getArgument(0)).getCartId(),
            PaymentStatus.SUCCESS));

        Order result = orderService.placeOrder(ORDER_CONTEXT);

        verify(paymentService, times(1)).processPayment(any());

        assertEquals(CART_ID, result.getCartId());
        assertEquals(CUSTOMER_REFERENCE, result.getConsumerReference());
        assertEquals(TOTAL_PRICE, result.getTotalPrice());
        assertEquals(TOTAL_PRICE, result.getTotalPrice());
        assertEquals(DEFAULT_ORDER_ENTRY, result.getEntries().getFirst());

    }

    private static OrderContext buildOrderContext() {
        return OrderContext.builder()
            .cartId(CART_ID)
            .customerReference(CUSTOMER_REFERENCE)
            .totalPrice(TOTAL_PRICE)
            .entries(List.of(DEFAULT_ORDER_ENTRY))
            .build();
    }

    private static OrderEntry buildOrderEntry() {
        return OrderEntry.builder().amount(1).productType(ProductType.COSMIC_CATNIP).build();
    }

    private PaymentTransaction buildPaymentTransaction(String cartId, PaymentStatus paymentStatus) {
        return PaymentTransaction.builder().id(UUID.randomUUID()).cartId(cartId).status(paymentStatus).build();
    }


}
