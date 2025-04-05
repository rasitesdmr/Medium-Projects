package com.rasitesdmr.springboottransactionmanagement.stock;
import com.rasitesdmr.springboottransactionmanagement.product.Product;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
