package com.dawood.hotelbooking.service.interfac;

import com.dawood.hotelbooking.dto.LoginDTO;
import com.dawood.hotelbooking.dto.Response;
import com.dawood.hotelbooking.entity.User;

public interface IUserService {
  Response register(User user);
  Response login(LoginDTO loginDTO);
  Response deleteUser(String userId);
  Response getAllUser();
  Response getUserById(String userId);
  Response getUserBookingHistory(String userId);
  Response getInfo(String email);
}
