package kafka.event;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelBookedEvent {
    private UUID hotelId;
    private UUID bookingId;
    private String userId;
    private String hotelStatus;
}
