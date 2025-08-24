package kafka.event;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentStartEvent {
    private UUID bookingId;
    private String userId;
    private BigDecimal amountTotal;
}
