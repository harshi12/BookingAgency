package com.booking.agency.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingRequestDTO {
    private Integer operatorID;
    private int customerID;
    @Setter
    private LocalDate bookingDate;
    private int bookingStartHour;
    private int bookingEndHour;

    public Optional<Integer> getOperatorID(){
        return Optional.ofNullable(operatorID);
    }
}
