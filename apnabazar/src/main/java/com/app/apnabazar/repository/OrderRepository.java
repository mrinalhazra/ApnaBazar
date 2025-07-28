package com.app.apnabazar.repository;

import com.app.apnabazar.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
