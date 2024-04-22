package com.booking.agency.service;

import com.booking.agency.models.Slot;

import java.time.LocalDate;
import java.util.List;

public interface SlotService {
    Slot getSlotById(String slotID);
    List<Slot> getAvailableSlotsForOperator(int operatorID, LocalDate date);
//    List<Slot> getAvailableSlots(String date);
//    List<Slot> getBookedSlotsForOperator(int operatorID, String date);
}