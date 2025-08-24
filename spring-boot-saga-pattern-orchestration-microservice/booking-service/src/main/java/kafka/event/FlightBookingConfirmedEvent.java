package kafka.event;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightBookingConfirmedEvent {
    private UUID bookingId;
    private UUID flightId;
    private String flightStatus;
}
