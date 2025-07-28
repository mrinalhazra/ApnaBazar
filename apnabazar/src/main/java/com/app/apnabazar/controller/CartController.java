package com.app.apnabazar.controller;

import com.app.apnabazar.model.Cart;
import com.app.apnabazar.service.CartService;
import com.app.apnabazar.dto.CartDto;
import com.app.apnabazar.dto.CartItemDto;
import com.app.apnabazar.dto.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseMessage createCart(@RequestBody CartDto cartDto){
        cartService.createCart(cartDto);
        return new ResponseMessage("Cart created successfully.");
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseMessage updateCart(@PathVariable Long id, @RequestBody CartDto updateCartDto){
        cartService.updateCart(id, updateCartDto);
        return new ResponseMessage("Cart updated successfully.");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    public ResponseMessage deleteCart(@PathVariable Long id){
        cartService.deleteCart(id);
        return new ResponseMessage("Cart deleted successfully.");
    }

    @DeleteMapping("/product/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteProductFromCart(@PathVariable Long userId, @RequestHeader("productId") Long productId ){
        cartService.deleteItemFromCart(userId, productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/product/{userId}")
    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    public ResponseMessage addProductToCart(@PathVariable Long userId, @RequestBody CartItemDto cartItemDto){
        cartService.addToCart(userId, cartItemDto);
        return new ResponseMessage("Item added to cart");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public Cart getCartById(@PathVariable Long id){
        return cartService.getCart(id);
    }

}
