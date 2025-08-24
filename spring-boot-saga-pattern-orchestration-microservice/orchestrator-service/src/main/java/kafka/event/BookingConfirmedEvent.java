package kafka.event;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingConfirmedEvent {
    private UUID bookingId;
    private String bookingStatus;
}
