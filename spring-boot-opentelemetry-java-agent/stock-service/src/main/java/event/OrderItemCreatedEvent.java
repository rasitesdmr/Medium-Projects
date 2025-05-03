package event;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemCreatedEvent {

    private Long orderItemId;
    private int quantity;
    private BigDecimal totalPrice;
    private Long productId;
}
