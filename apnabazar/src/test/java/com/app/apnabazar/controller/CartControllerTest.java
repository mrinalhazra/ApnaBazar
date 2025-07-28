package com.app.apnabazar.controller;

import com.app.apnabazar.dto.CartDto;
import com.app.apnabazar.dto.CartItemDto;
import com.app.apnabazar.model.Cart;
import com.app.apnabazar.model.CartItem;
import com.app.apnabazar.model.Product;
import com.app.apnabazar.model.User;
import com.app.apnabazar.service.CartService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

@WebMvcTest(controllers = CartController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CartControllerTest {

    @Mock
    private CartService cartService;

    @Autowired
    private MockMvc mockMvc;

    private Cart cart;

    @BeforeAll
    public void setup(){
        cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setId(2L);
        cartItem.setPrice(100);
        cartItem.setQuantity(2);
        cartItem.setProduct(new Product());
        cartItem.setCart(cart);

        cart.setId(1L);
        cart.setUser(new User());
        cart.setItems(Arrays.asList(cartItem));
        cart.setTotalPrice(200);
    }

    @Test
    @DisplayName("Testing the Get call for cart")
    @WithMockUser(roles = "ADMIN")
    public void shouldTestGetCartById(){

        Mockito.when(cartService.getCart(1L)).thenReturn(cart);
        try {
            mockMvc.perform(get("api/cart/1")).andExpect(status().is2xxSuccessful());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }


    }

    @Test
    @DisplayName("Testing create cart api call")
    public void shouldTestCreateCart() {
        CartDto cartDto  = new CartDto();
        cartDto.setUserId(1l);
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setQuantity(1);
        cartItemDto.setProductId(1L);
        cartDto.setCartItemList(Arrays.asList(cartItemDto));

        Mockito.when("/api/cart").thenReturn("Cart APi is working properly.");
        try {
            mockMvc.perform(post("/api/cart")).andExpect(status().isCreated());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
