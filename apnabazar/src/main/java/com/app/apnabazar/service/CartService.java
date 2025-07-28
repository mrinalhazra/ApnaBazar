package com.app.apnabazar.service;

import com.app.apnabazar.exception.NotFoundException;
import com.app.apnabazar.model.Cart;
import com.app.apnabazar.model.CartItem;
import com.app.apnabazar.model.Product;
import com.app.apnabazar.model.User;
import com.app.apnabazar.repository.CartRepository;
import com.app.apnabazar.dto.CartDto;
import com.app.apnabazar.dto.CartItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    public void createCart(CartDto cartDto) {
        Cart cart = new Cart();
        User user = userService.getUserById(cartDto.getUserId());
        if(user != null){
            cart.setUser(user);
            List<CartItem> itemList = new ArrayList<>();
            double totalPrice = 0;
            for(CartItemDto cartItemDto : cartDto.getCartItemList()){
                CartItem item = new CartItem();
                Product product = productService.getProduct(cartItemDto.getProductId());
                item.setProduct(product);
                item.setPrice(product.getPrice());
                item.setQuantity(cartItemDto.getQuantity());
                item.setCart(cart);

                itemList.add(item);
                totalPrice += ( product.getPrice() * cartItemDto.getQuantity() );
            }
            cart.setItems(itemList);
            cart.setTotalPrice(totalPrice);

            cartRepository.save(cart);

        } else throw new NotFoundException("User does not exists.");

    }

    public void updateCart(Long id, CartDto updateCartDto) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new NotFoundException("Cart does not exists."));

        cart.setUser(userService.getUserById(updateCartDto.getUserId()));
        List<CartItem> itemList = new ArrayList<>();
        double updatedPrice = 0;
        for(CartItemDto cartItemDto : updateCartDto.getCartItemList()){
            CartItem item = new CartItem();
            item.setProduct(productService.getProduct(cartItemDto.getProductId()));
            item.setQuantity(cartItemDto.getQuantity());
            item.setCart(cart);
            item.setPrice(productService.getProduct(cartItemDto.getProductId()).getPrice());

            itemList.add(item);
            updatedPrice += (item.getPrice() * item.getQuantity());
        }
        cart.setItems(itemList);
        cart.setTotalPrice(updatedPrice);

        cartRepository.save(cart);


    }

    public void deleteCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cart doest not exists."));

        cartRepository.deleteById(id);
    }

    public void deleteItemFromCart(Long userId, Long productId) {
        User user = userService.getUserById(userId);
        if(user == null){
            throw new NotFoundException("User not found");
        }
        Cart cart = user.getCart();
        for(CartItem cartItem : cart.getItems()){
            if(cartItem.getProduct().getId().equals(productId)){
                cart.getItems().remove(cartItem);
                break;
            }
        }
        cartRepository.save(cart);
    }

    public void addToCart(Long userId, CartItemDto cartItemDto) {
        Cart cart = userService.getUserById(userId).getCart();
        if(cart == null){
            throw new NotFoundException("cart not found");
        }

        CartItem cartItem = new CartItem();
        Product productOptional = productService.getProduct(cartItemDto.getProductId());
        cartItem.setProduct(productOptional);
        cartItem.setPrice(productOptional.getPrice() * cartItemDto.getQuantity());
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setCart(cart);

        cart.getItems().add(cartItem);

        cartRepository.save(cart);

    }

    public Cart getCart(Long id) {
        try {
            return cartRepository.findById(id).get();
        } catch (Exception e) {
            throw new NotFoundException("Cart does not exists.");
        }
    }
}
