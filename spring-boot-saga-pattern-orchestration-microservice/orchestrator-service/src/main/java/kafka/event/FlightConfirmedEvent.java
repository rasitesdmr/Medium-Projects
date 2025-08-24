package kafka.event;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightConfirmedEvent {
    private UUID flightId;
    private UUID bookingId;
    private String userId;
    private String flightStatus;
}
