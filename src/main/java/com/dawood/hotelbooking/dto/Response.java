package com.dawood.hotelbooking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class Response {
  private int statusCode;
  private String message;

  private String token;
  private String role;
  private String bookingCode;
  private String expirationTime;

  private UserDTO user;
  private RoomDTO room;
  private BookingDTO booking;
  private List<UserDTO> users;
  private List<RoomDTO> rooms;
  private List<BookingDTO> bookings;
}
