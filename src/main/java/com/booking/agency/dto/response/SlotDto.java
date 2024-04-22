package com.booking.agency.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SlotDto {
    private int startHour;
    private int endHour;
}
