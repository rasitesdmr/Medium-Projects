package kafka.event;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightStartEvent {
    private UUID bookingId;
    private String userId;
    private String flightFrom;
    private String flightTo;
    private LocalDate flightDate;
    private String flightNo;
}
