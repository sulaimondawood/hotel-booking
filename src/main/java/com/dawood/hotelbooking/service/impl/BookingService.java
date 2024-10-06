package com.dawood.hotelbooking.service.impl;

import com.dawood.hotelbooking.dto.BookingDTO;
import com.dawood.hotelbooking.dto.Response;
import com.dawood.hotelbooking.entity.Booking;
import com.dawood.hotelbooking.entity.Room;
import com.dawood.hotelbooking.entity.User;
import com.dawood.hotelbooking.exceptions.BookingException;
import com.dawood.hotelbooking.repository.BookingRepository;
import com.dawood.hotelbooking.repository.RoomRepository;
import com.dawood.hotelbooking.repository.UserRepository;
import com.dawood.hotelbooking.service.interfac.IBookingService;
import com.dawood.hotelbooking.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {

  private final UserRepository userRepository;
  private final BookingRepository bookingRepository;
  private final RoomRepository roomRepository;

  @Override
  public Response createBooking(Long roomId, Long userId, Booking booking) {
    Response response = new Response();
    try {
      if (booking.getCheckOutDate().isBefore(booking.getCheckInDate())) {
        throw new IllegalArgumentException("Check in date must come before check out date");
      }
      Room room = roomRepository.findById(roomId).orElseThrow(() -> new BookingException("Room not found"));
      User user = userRepository.findById(userId).orElseThrow(() -> new BookingException("User not found"));
      List<Booking> existingBookings = room.getBookings();

      if (!isRoomAvailable( booking, existingBookings)) {
        throw new BookingException("Room not available");
      }
      String confirmationCode = Utils.generateRandomString(10);
      booking.setRoom(room);
      booking.setUser(user);
      booking.setBookingCode(confirmationCode);
      bookingRepository.save(booking);

      response.setMessage("Succesfull");
      response.setStatusCode(201);
      response.setBookingCode(confirmationCode);

    } catch (BookingException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());
    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error creating a booking " + e.getMessage());
    }
    return response;
  }

  @Override
  public Response getAllBookings() {
    Response response = new Response();
    List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    List<BookingDTO> bookingDTOList = Utils.mapBookingListToBookingListDTO(bookingList);

    response.setMessage("Successful");
    response.setStatusCode(200);
    response.setBookings(bookingDTOList);

    return response;
  }

  @Override
  public Response findByBookingByConfirmationCode(String confirmationCode) {
    Response response = new Response();

    try {
      Booking booking = bookingRepository.findBookingByBookingCode(confirmationCode).orElseThrow(()->new BookingException("Booking not found"));
      BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTO(booking);

      response.setMessage("Successful");
      response.setStatusCode(200);
      response.setBooking(bookingDTO);
    }catch (BookingException e){
      response.setMessage(e.getMessage());
      response.setStatusCode(404);
    }catch (Exception e){
      response.setMessage("Error getting booking "+e.getMessage());
      response.setStatusCode(500);
    }
    return response;
  }

  @Override
  public Response cancelBooking(Long bookingId) {
    Response response = new Response();

    try {
      Booking booking = bookingRepository.findById(bookingId).orElseThrow(()->new BookingException("Booking not found"));
      bookingRepository.deleteById(bookingId);
      BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTO(booking);

      response.setStatusCode(200);
      response.setMessage("Successful");
      response.setBooking(bookingDTO);
    }catch (BookingException e){
      response.setStatusCode(404);
      response.setMessage(e.getMessage());
    }catch (Exception e){
      response.setMessage("Error cancelling booking "+e.getMessage());
      response.setStatusCode(500);
    }

    return response;
  }


  private boolean isRoomAvailable(Booking booking, List<Booking> existingBookings) {
    return existingBookings.stream().noneMatch(existingBooking ->
        booking.getCheckInDate().equals(existingBooking.getCheckInDate())
            || booking.getCheckInDate().isAfter(existingBooking.getCheckInDate())
            || booking.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
            && booking.getCheckInDate().isBefore(existingBooking.getCheckOutDate())
            || booking.getCheckInDate().isBefore(existingBooking.getCheckInDate())

            && booking.getCheckOutDate().equals(existingBooking.getCheckOutDate())
            || booking.getCheckInDate().isBefore(existingBooking.getCheckInDate())

            && booking.getCheckOutDate().isAfter(existingBooking.getCheckOutDate())

            || booking.getCheckInDate().equals(existingBooking.getCheckOutDate())
            && booking.getCheckOutDate().equals(existingBooking.getCheckInDate())

            || booking.getCheckInDate().equals(existingBooking.getCheckOutDate())
            && booking.getCheckOutDate().equals(booking.getCheckInDate())

    );

  }
}
