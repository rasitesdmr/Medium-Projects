package kafka.event;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentBookingFailedEvent {
    private UUID bookingId;
    private UUID paymentId;
    private String paymentStatus;
    private String bookingStatus;
}
