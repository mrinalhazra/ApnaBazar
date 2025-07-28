package com.app.apnabazar.service;

import com.app.apnabazar.exception.NotFoundException;
import com.app.apnabazar.model.*;
import com.app.apnabazar.repository.OrderItemRepository;
import com.app.apnabazar.repository.OrderRepository;
import com.app.apnabazar.dto.CartDto;
import com.app.apnabazar.dto.CartItemDto;
import com.app.apnabazar.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    public void createOrder(Long userId, CartDto cartDto) {
        if(userId.equals(cartDto.getUserId())){
            Order order = new Order();
            User user = userService.getUserById(userId);
            if(user == null) throw new NotFoundException("User not found/ does not exists.");
            order.setUser(user);
            List<OrderItem> orderItemList = new ArrayList<>();
            double totalPrice = 0;
            for(CartItemDto cartItemDto : cartDto.getCartItemList()){
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setProduct(productService.getProduct(cartItemDto.getProductId()));
                orderItem.setQuantity(cartItemDto.getQuantity());

                cartService.deleteItemFromCart(userId, cartItemDto.getProductId());

                orderItemList.add(orderItem);
                totalPrice += (orderItem.getProduct().getPrice() * orderItem.getQuantity());
            }
            order.setOrderItems(orderItemList);
            order.setTotalPrice(BigDecimal.valueOf(totalPrice));

            orderRepository.save(order);
            user.setCart(null);  // since order is placed hence empty the cart.

        }

    }

    public List<OrderItem> generateSummary(Long userId) {
        List<OrderItem> requiredSummaryOrders = new ArrayList<>();
        if(userService.getUserById(userId) != null){
            List<Order> orders = userService.getUserById(userId).getOrderList();
            for(int i=0; i<= orders.size(); i++){
                requiredSummaryOrders.addAll(orders.get(i).getOrderItems());
            }

        }else throw new NotFoundException("User not found.");

        return requiredSummaryOrders;
    }

    @Transactional
    public void placeOrder(OrderDto orderDto) {
        User user = null;
        Cart cart = null;
        try {
            user = userService.getUserById(orderDto.getUserId());
            cart = cartService.getCart(orderDto.getCartId());
        } catch (Exception e) {
            throw new NotFoundException("Unable to find cart/user, please check if not already exists.");
        }

        Order order = new Order();
        order.setUser(user);
        order.setCreatedDate(new Date());
        order.setShippingAddress(user.getAddress());
        order.setTotalPrice(BigDecimal.valueOf(cart.getTotalPrice()));

        List<OrderItem> orderItems = new ArrayList<>();
        cart.getItems().stream().forEach(cartItem -> {
             OrderItem orderItem = new OrderItem();
             orderItem.setProduct(cartItem.getProduct());
             orderItem.setQuantity(cartItem.getQuantity());
             orderItem.setOrder(order);
             orderItemRepository.save(orderItem);

             orderItems.add(orderItem);

        });
        order.setOrderItems(orderItems);
        cartService.deleteCart(orderDto.getCartId());
        order.setPaymentMethod(orderDto.getPaymentMethod());

        user.setCart(null);
        orderRepository.save(order);


    }
}
