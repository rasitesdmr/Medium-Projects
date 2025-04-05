package com.rasitesdmr.springboottransactionmanagement.stock.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockUpdateResponse {
    private Long productId;
    private int stockQuantity;
    private boolean isError;
}
