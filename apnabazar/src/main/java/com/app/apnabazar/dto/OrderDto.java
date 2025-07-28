package com.app.apnabazar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

        private Long userId;
        private Long cartId;
        private String paymentMethod;


}
