package event;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreatedEvent {

    private Long orderId;
    private Long userId;
    private String orderStatus;
    private List<OrderItemCreatedEvent> itemEvent;

}
