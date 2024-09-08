package com.dawood.hotelbooking.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {
  private Long Id;
  private String bookingCode;
  private LocalDate checkInDate;
  private LocalDate checkOutDate;
  private int numOfChildren;
  private int numOfAdults;
  private int totalGuest;
  private UserDTO user;
  private RoomDTO room;

}
