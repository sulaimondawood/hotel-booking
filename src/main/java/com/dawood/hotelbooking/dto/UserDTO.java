package com.dawood.hotelbooking.dto;

import com.dawood.hotelbooking.entity.Booking;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
  private Long id;
  private String email;
  private String fullname;
//  private String password;
  private String phoneNumber;
  private String role;
  private List<Booking> bookings;
}
