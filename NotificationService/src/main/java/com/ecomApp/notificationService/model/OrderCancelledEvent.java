package com.ecomApp.notificationService.model;

import java.time.LocalDateTime;
import java.util.List;

public record OrderCancelledEvent(
        String eventId,
        String orderNumber,
        String reason,
        List<OrderItem> orderItems,
        Customer customer,
        Address dileveryAddress,
        LocalDateTime createdAt
) {
}
