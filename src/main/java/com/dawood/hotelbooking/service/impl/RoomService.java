package com.dawood.hotelbooking.service.impl;

import com.dawood.hotelbooking.dto.Response;
import com.dawood.hotelbooking.dto.RoomDTO;
import com.dawood.hotelbooking.entity.Room;
import com.dawood.hotelbooking.exceptions.BookingException;
import com.dawood.hotelbooking.repository.RoomRepository;
import com.dawood.hotelbooking.service.CloudinaryService;
import com.dawood.hotelbooking.service.interfac.IRoomService;
import com.dawood.hotelbooking.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {

  private final RoomRepository roomRepository;
  private final CloudinaryService cloudinaryService;

  @Override
  public Response addRoom(String roomType, BigDecimal roomPrice, String roomDescription, MultipartFile roomPhoto) {
    Response response = new Response();
    try {
      String savedRoomPhoto = cloudinaryService.uploadFile(roomPhoto, roomPhoto.getName());
      Room room = new Room();
      room.setRoomDescription(roomDescription);
      room.setRoomType(roomType);
      room.setRoomPrice(roomPrice);
      room.setRoomPhotoUrl(savedRoomPhoto);

      Room savedRoom = roomRepository.save(room);
      RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);
      response.setRoom(roomDTO);
      response.setMessage("Successful");
      response.setStatusCode(201);

    }catch (Exception e ){
      response.setStatusCode(500);
      response.setMessage("Error saving a room " + e.getMessage());
    }
    return  response;
  }

  @Override
  public Response getAllRooms() {
    Response response = new Response();
    try {
      List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
      List<RoomDTO> roomDTOList = Utils.mapRoomListToRoomListDTO(roomList);
      response.setRooms(roomDTOList);
      response.setMessage("Successful");
      response.setStatusCode(200);
    }catch (Exception e ){
      response.setMessage("Error getting all rooms "+ e.getMessage());
      response.setStatusCode(500);
    }
    return response;
  }

  @Override
  public List<String> getAllRoomTypes() {
    return roomRepository.findDistinctRoomTypes();
  }

  @Override
  public Response deleteRoom(Long roomId) {
    Response response = new Response();
    try {
      roomRepository.findById(roomId).orElseThrow(()->new BookingException("Room does not exist"));
      roomRepository.deleteById(roomId);

      response.setMessage("Successful");
      response.setStatusCode(200);
    }catch (BookingException e){
      response.setMessage(e.getMessage());
      response.setStatusCode(400);
    }
    catch (Exception e){
      response.setMessage("Error deleting room " + e.getMessage());
      response.setStatusCode(500);
    }

    return  response;
  }

  @Override
  public Response getRoomById(Long roomId) {
    Response response = new Response();

    try {
      Room room = roomRepository.findById(roomId).orElseThrow(()->new BookingException("Room does not exist"));
      RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(room);

      response.setRoom(roomDTO);
      response.setMessage("Successful");
      response.setStatusCode(200);
    }catch (BookingException e){
      response.setMessage(e.getMessage());
      response.setStatusCode(404);
    }catch (Exception e){
      response.setMessage("Error getting room "+ e.getMessage());
      response.setStatusCode(500);
    }

    return  response;
  }

  @Override
  public Response updateRoom(Long roomId, String roomType, BigDecimal roomPrice, String roomDesc, MultipartFile roomPhoto) {
    Response response = new Response();
    try {
      String photoUrl = null;

      if(roomPhoto != null || !roomPhoto.isEmpty()){
         photoUrl = cloudinaryService.uploadFile(roomPhoto, roomPhoto.getOriginalFilename());
      }

      Room room = roomRepository.findById(roomId).orElseThrow(()-> new BookingException("Room does not exist"));
      if(roomType != null ) room.setRoomType(roomType);
      if(roomPrice != null) room.setRoomPrice(roomPrice);
      if(roomDesc != null)room.setRoomDescription(roomDesc);
      room.setRoomPhotoUrl(photoUrl);

      Room updatedRoom = roomRepository.save(room);
      RoomDTO updatedRoomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);

      response.setRoom(updatedRoomDTO);
      response.setMessage("Successful");
      response.setStatusCode(200);

    }catch (BookingException e ){
      response.setMessage(e.getMessage());
      response.setStatusCode(400);
    }catch (Exception e){
      response.setMessage("Error updating room "+ e.getMessage());
      response.setStatusCode(500);
    }
    return response;
  }

  @Override
  public Response getAvailableRooms() {
    Response response = new Response();
    try {
      List<Room> roomList = roomRepository.findAllAvailableRooms();
      List<RoomDTO> roomDTOList = Utils.mapRoomListToRoomListDTO(roomList);

      response.setMessage("Successful");
      response.setStatusCode(200);
      response.setRooms(roomDTOList);
    }catch (Exception e){
      response.setMessage("Error getting availableRooms "+ e.getMessage());
      response.setStatusCode(500);
    }
    return response;
  }

  @Override
  public Response getAvailableRoomsByDateAndTime(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
    Response response = new Response();

    try {
      List<Room> roomList = roomRepository.findAllAvailableRoomsByDataAndType(checkInDate,checkOutDate,roomType);
      List<RoomDTO> roomDTOList = Utils.mapRoomListToRoomListDTO(roomList);

      response.setMessage("Successful");
      response.setStatusCode(200);
      response.setRooms(roomDTOList);
    }catch (Exception e){
      response.setMessage("Error getting availableRooms "+ e.getMessage());
      response.setStatusCode(500);
    }
      return response;
  }
}
