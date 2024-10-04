package com.dawood.hotelbooking.service.interfac;

import com.dawood.hotelbooking.dto.Response;
import com.dawood.hotelbooking.entity.Booking;

public interface IBookingService {
  Response createBooking(Long roomId, Long userId, Booking booking);
  Response getAllBookings();
  Response findByBookingByConfirmationCode(String confirmationCode);
  Response cancelBooking(Long bookingId);

}
