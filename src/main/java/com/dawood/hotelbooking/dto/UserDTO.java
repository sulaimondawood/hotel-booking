package com.dawood.hotelbooking.dto;

import com.dawood.hotelbooking.entity.Booking;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
  private Long id;
  private String email;
  private String fullname;
  private String password;
  private String phoneNumber;
  private String role;
  private List<BookingDTO> bookings;
}
