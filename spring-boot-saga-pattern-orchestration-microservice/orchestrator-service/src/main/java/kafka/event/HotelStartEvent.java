package kafka.event;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelStartEvent {
    private UUID bookingId;
    private String userId;
    private String hotelCode;
    private LocalDate checkIn;
    private LocalDate checkOut;
}
