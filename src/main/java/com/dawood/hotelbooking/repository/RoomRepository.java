package com.dawood.hotelbooking.repository;

import com.dawood.hotelbooking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long> {

  @Query("SELECT DISTINCT r.roomType FROM Room r")
  List<String> findDistinctRoomTypes();

  @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT b.room.id FROM Booking b)")
  List<Room> findAllAvailableRooms();

  @Query("SELECT r from Room r WHERE r.roomType LIKE %:roomType% AND r.id NOT IN (SELECT bk.room.id FROM Booking bk WHERE" +
      "(bk.checkInDate <= :checkOutDate) AND (bk.checkOutDate >= :checkInDate))")
  List<Room> findAllAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
}
