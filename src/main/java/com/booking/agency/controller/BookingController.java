package com.booking.agency.controller;

import com.booking.agency.dto.request.CreateBookingRequestDTO;
import com.booking.agency.dto.request.UpdateBookingRequestDTO;
import com.booking.agency.dto.response.BookingResponseDTO;
import com.booking.agency.dto.response.OperatorAvailableSlotsResponseDTO;
import com.booking.agency.dto.response.OperatorBookedSlotsResponseDTO;
import com.booking.agency.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static com.booking.agency.constants.Constants.BOOKING_ID;
import static com.booking.agency.constants.Constants.OPERATOR_ID;
import static com.booking.agency.constants.Routes.*;

@RestController
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping(OPERATOR_AVAILABLE_SLOTS_URL)
    public OperatorAvailableSlotsResponseDTO getAvailableSlotsForOperator(
            @PathVariable(OPERATOR_ID) int operatorID){
        return bookingService.getAvailableSlots(operatorID, getDate());
    }

    @GetMapping(OPERATOR_BOOKED_SLOTS_URL)
    public OperatorBookedSlotsResponseDTO getBookedSlotsForOperator(
            @PathVariable(OPERATOR_ID) int operatorID){
        return bookingService.getBookedSlots(operatorID, getDate());
    }

    @PostMapping(CREATE_BOOKING_URL)
    public BookingResponseDTO createBooking(@RequestBody CreateBookingRequestDTO requestDTO){
        requestDTO.setBookingDate(getDate());
        return bookingService.createBooking(requestDTO);
    }

    @PatchMapping(RESCHEDULE_BOOKING_URL)
    public BookingResponseDTO rescheduleBooking(@PathVariable(BOOKING_ID) int bookingID,
                                                @RequestBody UpdateBookingRequestDTO requestDTO){
        requestDTO.setBookingID(bookingID);
        requestDTO.setBookingDate(getDate());
        return bookingService.rescheduleBooking(requestDTO);
    }

    @PatchMapping(CANCEL_BOOKING_URL)
    public BookingResponseDTO cancelBooking(@PathVariable(BOOKING_ID) int bookingID){
        return bookingService.cancelBooking(bookingID);
    }

    private LocalDate getDate() {
        String dateString = "2024-01-01";
        return LocalDate.parse(dateString);
    }

}
