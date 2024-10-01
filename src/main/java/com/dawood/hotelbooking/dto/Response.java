package com.dawood.hotelbooking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
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
