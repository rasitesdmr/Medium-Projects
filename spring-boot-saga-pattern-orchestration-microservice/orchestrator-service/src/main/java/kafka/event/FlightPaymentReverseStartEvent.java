package kafka.event;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightPaymentReverseStartEvent {
    private UUID bookingId;
    private String userId;
    private String paymentStatus;
    private String flightStatus;
    private String bookingStatus;
}
