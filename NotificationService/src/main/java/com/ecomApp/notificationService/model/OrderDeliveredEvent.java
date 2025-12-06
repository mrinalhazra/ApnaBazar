package com.ecomApp.notificationService.model;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDeliveredEvent(
        String eventId,
        String orderNumber,
        List<OrderItem> orderItems,
        Customer customer,
        Address dileveryAddress,
        LocalDateTime createdAt
) {
}
