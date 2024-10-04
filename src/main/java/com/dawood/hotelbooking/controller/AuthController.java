package com.dawood.hotelbooking.controller;

import com.dawood.hotelbooking.dto.LoginDTO;
import com.dawood.hotelbooking.dto.Response;
import com.dawood.hotelbooking.entity.User;
import com.dawood.hotelbooking.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<Response> register(@RequestBody User user){
    Response response = userService.register(user);
    return ResponseEntity.status(response.getStatusCode()).body(response);

  }

  @PostMapping("/login")
  public  ResponseEntity<Response> login(@RequestBody LoginDTO loginDTO){
    Response response = userService.login(loginDTO);
    return  ResponseEntity.status(response.getStatusCode()).body(response);
  }
}
