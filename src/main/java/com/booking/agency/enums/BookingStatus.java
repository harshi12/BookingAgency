package com.booking.agency.enums;

public enum BookingStatus {
    BOOKED,
    CANCELLED;

    public static BookingStatus fromString(String status) {
        for (BookingStatus bookingStatus : BookingStatus.values()) {
            if (bookingStatus.name().equalsIgnoreCase(status)) {
                return bookingStatus;
            }
        }
        return null;
    }
}
