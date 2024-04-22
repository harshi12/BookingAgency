package com.booking.agency.service;

import com.booking.agency.dto.request.CreateBookingRequestDTO;
import com.booking.agency.dto.request.UpdateBookingRequestDTO;
import com.booking.agency.dto.response.BookingResponseDTO;
import com.booking.agency.dto.response.OperatorAvailableSlotsResponseDTO;
import com.booking.agency.dto.response.OperatorBookedSlotsResponseDTO;

import java.time.LocalDate;

public interface BookingService {
    BookingResponseDTO createBooking(CreateBookingRequestDTO requestDTO);
    BookingResponseDTO rescheduleBooking(UpdateBookingRequestDTO requestDTO);
    BookingResponseDTO cancelBooking(int bookingID);
    BookingResponseDTO getBooking(int bookingID);
    OperatorAvailableSlotsResponseDTO getAvailableSlots(int operatorID, LocalDate bookingDate);
    OperatorBookedSlotsResponseDTO getBookedSlots(int operatorID, LocalDate bookingDate);
}
