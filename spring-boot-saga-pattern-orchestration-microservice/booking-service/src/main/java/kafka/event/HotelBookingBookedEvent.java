package kafka.event;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelBookingBookedEvent {
    private UUID bookingId;
    private UUID hotelId;
    private String hotelStatus;
}
