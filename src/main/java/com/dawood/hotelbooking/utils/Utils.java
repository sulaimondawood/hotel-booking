package com.dawood.hotelbooking.utils;

import com.dawood.hotelbooking.dto.BookingDTO;
import com.dawood.hotelbooking.dto.RoomDTO;
import com.dawood.hotelbooking.dto.UserDTO;
import com.dawood.hotelbooking.entity.Booking;
import com.dawood.hotelbooking.entity.Room;
import com.dawood.hotelbooking.entity.User;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
  private static final String ALPHA_NUMERIC = "ABCSEFGHJIKLEMNPQRSTUVWXYZ123456789";
  private static final SecureRandom secureRandom= new SecureRandom();

  public static String generateRandomString(int length){
    StringBuilder stringBuilder = new StringBuilder();

    for(int i=0; i< length; i++){
      char randomChar = ALPHA_NUMERIC.charAt(secureRandom.nextInt(ALPHA_NUMERIC.length()));
      stringBuilder.append(randomChar);
    }
    return stringBuilder.toString();
  }

  public static UserDTO mapUserEntityTOUserDTO(User user){
    UserDTO userDTO = new UserDTO();
    userDTO.setId(user.getId());
    userDTO.setFullname(user.getFullname());
    userDTO.setEmail(user.getEmail());
    userDTO.setPhoneNumber(user.getPhoneNumber());
    userDTO.setRole(user.getRole());
    return  userDTO;
  }

  public static RoomDTO mapRoomEntityToRoomDTO(Room room){
    if(room == null) return  null;

    RoomDTO roomDTO = new RoomDTO();
    roomDTO.setId(room.getId());
    roomDTO.setRoomDescription(room.getRoomDescription());
    roomDTO.setRoomType(room.getRoomType());
    roomDTO.setRoomPrice(room.getRoomPrice());
    roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
    return  roomDTO;
  }

  public static BookingDTO mapBookingEntityToBookingDTO(Booking booking){
    if(booking == null) return  null;
    BookingDTO bookingDTO = new BookingDTO();
    bookingDTO.setId(booking.getId());
    bookingDTO.setBookingCode(booking.getBookingCode());
    bookingDTO.setCheckInDate(booking.getCheckInDate());
    bookingDTO.setCheckOutDate(booking.getCheckOutDate());
    bookingDTO.setNumOfAdults(booking.getNumOfAdults());
    bookingDTO.setNumOfChildren(booking.getNumOfChildren());
    bookingDTO.setTotalGuest(booking.getTotalGuest());
    return  bookingDTO;
  }

  public static RoomDTO mapRoomEntityToRoomDTOWithBookings(Room room){
    RoomDTO roomDTO = mapRoomEntityToRoomDTO(room);
    if(room.getBookings() != null){
      roomDTO.setBookings(room.getBookings().stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList()));
    }
    return  roomDTO;
  }

  public static BookingDTO mapBookingEntityToBookingDTOWithBookedRooms(Booking booking, boolean mapUser ){
    BookingDTO bookingDTO = mapBookingEntityToBookingDTO(booking);
    if(mapUser){
      bookingDTO.setUser(mapUserEntityTOUserDTO(booking.getUser()));
    }
    if(booking.getRoom() != null ){
      RoomDTO roomDTO = new RoomDTO();
      roomDTO.setRoomDescription(booking.getRoom().getRoomDescription());
      roomDTO.setRoomType(booking.getRoom().getRoomType());
      roomDTO.setRoomPhotoUrl(booking.getRoom().getRoomPhotoUrl());
      roomDTO.setRoomPrice(booking.getRoom().getRoomPrice());
      roomDTO.setId(booking.getRoom().getId());
      bookingDTO.setRoom(roomDTO);
    }

    return bookingDTO;
  }

  public static UserDTO mapUserEntitytoUserDTOWithUserBookings(User user){
    UserDTO userDTO = new UserDTO();
    userDTO.setId(user.getId());
    userDTO.setFullname(user.getFullname());
    userDTO.setEmail(user.getEmail());
    userDTO.setPhoneNumber(user.getPhoneNumber());
    userDTO.setRole(user.getRole());

    if(!userDTO.getBookings().isEmpty() ){
      userDTO.setBookings(user.getBookings().stream().map(booking->mapBookingEntityToBookingDTOWithBookedRooms(booking,false)).collect(Collectors.toList()));
    }

    return  userDTO;
  }

  public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList){
    return userList.stream().map(Utils::mapUserEntityTOUserDTO).collect(Collectors.toList());
  }

  public static  List<BookingDTO> mapBookingListToBookingListDTO(List<Booking> bookingList){
    return  bookingList.stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList());

  }

  public  static  List<RoomDTO> mapRoomListToRoomListDTO(List<Room> roomList){
    return  roomList.stream().map(Utils::mapRoomEntityToRoomDTO).collect(Collectors.toList());
  }
}
