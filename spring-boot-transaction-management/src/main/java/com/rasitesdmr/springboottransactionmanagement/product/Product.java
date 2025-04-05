package com.rasitesdmr.springboottransactionmanagement.product;

import com.rasitesdmr.springboottransactionmanagement.cartitem.CartItem;
import com.rasitesdmr.springboottransactionmanagement.orderitem.OrderItem;
import com.rasitesdmr.springboottransactionmanagement.stock.Stock;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

}
