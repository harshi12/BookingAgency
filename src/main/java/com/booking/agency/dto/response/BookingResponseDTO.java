package com.booking.agency.dto.response;

import com.booking.agency.enums.BookingStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Getter
public class BookingResponseDTO {
    private int id;
    private int serviceOperatorID;
    private int customerID;
    private BookingStatus status;
    private LocalDate bookingDate;
    private int bookingStartHour;
    private int bookingEndHour;
}
