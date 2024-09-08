package com.dawood.hotelbooking.dto;

import com.dawood.hotelbooking.entity.Booking;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {
  private Long id;
  private String roomType;
  private BigDecimal roomPrice;
  private  String roomDescription;
  private String roomPhotoUrl;
  private List<Booking> bookings;
}
