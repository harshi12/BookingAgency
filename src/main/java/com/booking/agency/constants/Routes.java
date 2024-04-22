package com.booking.agency.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Routes {
    public static final String OPERATOR_AVAILABLE_SLOTS_URL = "/operators/{operatorID}/availableSlots";
    public static final String OPERATOR_BOOKED_SLOTS_URL = "/operators/{operatorID}/bookedSlots";
    public static final String CREATE_BOOKING_URL = "/booking";
    public static final String CANCEL_BOOKING_URL = "/booking/{bookingID}/cancel";
    public static final String RESCHEDULE_BOOKING_URL = "/booking/{bookingID}/reschedule";
}
