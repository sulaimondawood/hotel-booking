package com.dawood.hotelbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Booking {

  @Id
  @GeneratedValue
  private Long id;
  private String bookingCode;
  @NotNull(message = "Check-in date is reqiured")
  private LocalDate checkInDate;
  @Future(message = "Check-out date must be in the future")
  private LocalDate checkOutDate;
  @Min(value = 1, message = "Number of adults must not be less than one")
  private int numOfAdults;
  @Min(value = 0,message = "Number of children must not be less than zero(0)")
  private int numOfChildren;
  private int totalGuest;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;
  @ManyToOne
  @JoinColumn(name = "room_id")
  private Room room;
  @LastModifiedDate
  @Column(insertable = false, nullable = false)
  private LocalDateTime updatedAt;
  @Column(nullable = false)
  @CreatedDate
  private LocalDateTime createdAt;

  public void calculateTotalGuest(){
    this.totalGuest = numOfAdults+numOfChildren;
  }

  public void setNumOfAdults(int numOfAdults) {
    this.numOfAdults = numOfAdults;
    calculateTotalGuest();
  }

  public  void setNumOfChildren(int numOfChildren){
    this.numOfChildren = numOfChildren;
    calculateTotalGuest();
  }
}
