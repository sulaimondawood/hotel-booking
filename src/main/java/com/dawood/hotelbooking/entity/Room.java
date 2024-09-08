package com.dawood.hotelbooking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {
  @Id
  @GeneratedValue
  private Long id;
  private String roomType;
  private BigDecimal roomPrice;
  private  String roomDescription;
  private String roomPhotoUrl;
  @OneToMany(cascade = CascadeType.ALL,mappedBy = "room")
  private List<Booking> bookings;
}
