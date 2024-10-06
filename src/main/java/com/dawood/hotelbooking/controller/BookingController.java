package com.dawood.hotelbooking.controller;

import com.dawood.hotelbooking.dto.Response;
import com.dawood.hotelbooking.entity.Booking;
import com.dawood.hotelbooking.service.interfac.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
  private final IBookingService bookingService;

  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN) or hasAuthority('USER')")
  public ResponseEntity<Response> getBookings(){
    Response response = bookingService.getAllBookings();
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @PostMapping("/{userId}/{roomId}")
  @PreAuthorize("hasAuthority('ADMIN) or hasAuthority('USER')")
  public ResponseEntity<Response> createBooking(@PathVariable(value = "userId", required = true) Long userId,
                                                @PathVariable(value = "roomId", required = true) Long roomId,
                                                @RequestBody Booking bookingRequest){
    Response response = bookingService.createBooking(roomId,userId,bookingRequest);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/{confirmationCode}")
  public ResponseEntity<Response> getBookingByConfirmationCode(@PathVariable String confirmationCode){
    Response response = bookingService.findByBookingByConfirmationCode(confirmationCode);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @DeleteMapping("/{bookingId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Response> cancelBooking(@PathVariable Long bookingId){
    Response response = bookingService.cancelBooking(bookingId);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

}
