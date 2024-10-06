package com.dawood.hotelbooking.controller;

import com.dawood.hotelbooking.dto.Response;
import com.dawood.hotelbooking.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @GetMapping("")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Response> getAllUsers() {
    Response response = userService.getAllUser();
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response> getUser(@PathVariable("id") String id) {
    Response response = userService.getUserById(id);
    return ResponseEntity.status(response.getStatusCode())
        .body(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Response> deleteUser(@PathVariable("id") String id) {
    Response response = userService.deleteUser(id);
    return ResponseEntity.status(response.getStatusCode())
        .body(response);
  }

  @GetMapping("/userInfo")
  public ResponseEntity<Response> getInfo(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    Response response = userService.getInfo(email);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/{id}/bookings")
  public ResponseEntity<Response> getUserBookings(@PathVariable String id){
    Response response = userService.getUserBookingHistory(id);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }
}
