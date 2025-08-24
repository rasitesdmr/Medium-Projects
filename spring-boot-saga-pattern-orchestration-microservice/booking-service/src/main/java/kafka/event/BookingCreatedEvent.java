package kafka.event;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingCreatedEvent {
    private UUID bookingId;
    private String userId;
    private BigDecimal amountTotal;
    private String flightFrom;
    private String flightTo;
    private LocalDate flightDate;
    private String flightNo;
    private String hotelCode;
    private LocalDate checkIn;
    private LocalDate checkOut;
}