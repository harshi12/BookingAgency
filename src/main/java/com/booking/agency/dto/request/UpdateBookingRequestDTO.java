package com.booking.agency.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Getter
public class UpdateBookingRequestDTO {
    @Setter
    private int bookingID;
    @Setter
    private LocalDate bookingDate;
    private int bookingStartHour;
    private int bookingEndHour;
}
