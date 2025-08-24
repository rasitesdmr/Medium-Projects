package kafka.event;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentBookingCompletedEvent {
    private UUID bookingId;
    private UUID paymentId;
    private String paymentStatus;
}
