package com.rasitesdmr.springboottransactionmanagement.cartitem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rasitesdmr.springboottransactionmanagement.cart.Cart;
import com.rasitesdmr.springboottransactionmanagement.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;


}
