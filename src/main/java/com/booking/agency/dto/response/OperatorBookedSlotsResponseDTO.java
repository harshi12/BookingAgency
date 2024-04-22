package com.booking.agency.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class OperatorBookedSlotsResponseDTO {
    private int operatorID;
    private List<SlotDto> bookedSlots;
}
