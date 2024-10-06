package com.dawood.hotelbooking.controller;

import com.dawood.hotelbooking.dto.Response;
import com.dawood.hotelbooking.service.interfac.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rooms")
public class RoomController {

  private final IRoomService roomService;

  @GetMapping
  public ResponseEntity<Response> getAllRooms(){
    Response response = roomService.getAllRooms();
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Response> addRoom(@RequestParam(value = "roomPhoto", required = false)MultipartFile roomPhoto,
                                          @RequestParam(value = "roomPrice", required = false)BigDecimal roomPrice,
                                          @RequestParam(value = "roomType", required = false) String roomType,
                                          @RequestParam(value = "roomDescription", required = false) String roomDescription){
    if(roomPhoto == null || roomPhoto.isEmpty() ||  roomType==null || roomType.isEmpty() || roomPrice==null  ){
      Response response = new Response();
      response.setStatusCode(400);
      response.setMessage("Please provide values for required fields(roomPhoto,Type & Price");
    }
    Response response = roomService.addRoom(roomType,roomPrice,roomDescription,roomPhoto);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/types")
  public List<String> getAllRoomTypes(){
  return roomService.getAllRoomTypes();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response> getRoomById(@PathVariable(value = "id") Long id){
    Response response = roomService.getRoomById(id);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/available-rooms")
  public  ResponseEntity<Response> getAvailableRooms(){
    Response response = roomService.getAvailableRooms();
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/available-rooms/datetime")
  public ResponseEntity<Response> getAvailableRoomsByDateAndTime(@RequestParam(value = "checkInDate", required = false ) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate checkInDate,
                                                                 @RequestParam(value = "checkOutDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate checkOutDate,
                                                                 @RequestParam(value = "roomType", required = false) String roomType){
    if(checkInDate == null || checkOutDate == null || roomType.isEmpty()){
      Response response = new Response();
      response.setMessage("All fields are required");
      response.setStatusCode(400);
    }
    Response response = roomService.getAvailableRoomsByDateAndTime(checkInDate, checkOutDate,roomType);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Response> updateRoom(@RequestParam(value = "roomPhoto", required = false) MultipartFile roomPhoto,
                                             @RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice,
                                             @RequestParam(value = "roomType", required = false) String roomType,
                                             @RequestParam(value = "roomDescription", required = false) String roomDescription,
                                             @PathVariable Long id){
    Response response = roomService.updateRoom(id,roomType,roomPrice,roomDescription,roomPhoto);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Response> deleteRoom(@PathVariable Long id){
    Response response = roomService.deleteRoom(id);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }
}
