package com.booking.agency.models;

import com.booking.agency.dto.response.SlotDto;
import com.booking.agency.entity.SlotEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Getter
public class Slot {
    private String id;
    private int startHour;
    private int endHour;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public static Slot buildFrom(SlotEntity entity) {
        return Slot.builder()
                .id(entity.getId())
                .startHour(entity.getStartHour())
                .endHour(entity.getEndHour())
                .date(entity.getDate())
                .build();
    }

    public static SlotDto mapToSlotDTO(Slot slot) {
        return SlotDto.builder()
                .startHour(slot.getStartHour())
                .endHour(slot.getEndHour())
                .build();
    }

    public void mergeSlotHour(Slot slot){
        this.endHour = slot.endHour;
    }
}
