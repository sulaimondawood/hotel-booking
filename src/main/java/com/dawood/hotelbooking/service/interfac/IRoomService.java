package com.dawood.hotelbooking.service.interfac;

import com.dawood.hotelbooking.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
  Response addRoom(String roomType, BigDecimal roomPrice, String roomDescription, MultipartFile roomPhoto);
  Response getAllRooms();
  List<String> getAllRoomTypes();
  Response deleteRoom(Long roomId);
  Response getRoomById(Long roomId);
  Response updateRoom(Long roomId, String roomType, BigDecimal roomPrice, String roomDesc, MultipartFile roomPhoto);
  Response getAvailableRooms();
  Response getAvailableRoomsByDateAndTime(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
}
