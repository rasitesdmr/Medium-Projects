package com.rasitesdmr.springboottransactionmanagement.cartitem.controller;

import com.rasitesdmr.springboottransactionmanagement.cartitem.CartItem;
import com.rasitesdmr.springboottransactionmanagement.cartitem.service.CartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/cart_items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<CartItem>> getAllByCartId(@RequestParam Long cartId){
        return new ResponseEntity<>(cartItemService.getAllByCartId(cartId), HttpStatus.OK);
    }
}
