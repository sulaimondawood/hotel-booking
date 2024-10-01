package com.dawood.hotelbooking.service.impl;

import com.dawood.hotelbooking.dto.LoginDTO;
import com.dawood.hotelbooking.dto.Response;
import com.dawood.hotelbooking.dto.UserDTO;
import com.dawood.hotelbooking.entity.User;
import com.dawood.hotelbooking.exceptions.BookingException;
import com.dawood.hotelbooking.repository.UserRepository;
import com.dawood.hotelbooking.service.interfac.IUserService;
import com.dawood.hotelbooking.utils.JWTService;
import com.dawood.hotelbooking.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {


  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final JWTService jwtService;

  @Override
  public Response register(User user) {
    Response response = new Response();

    try {
      if(user.getRole()== null || user.getRole().isBlank()){
        user.setRole("USER");
      }

      if(userRepository.existsByEmail(user.getEmail())){
        throw new BookingException(user.getEmail()+ " "+ "already exists");
      }
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      User savedUser= userRepository.save(user);
      UserDTO userDTO = Utils.mapUserEntityTOUserDTO(user);

      response.setStatusCode(200);
      response.setMessage("Successful");
      response.setUser(userDTO);


    }catch (BookingException e){
      response.setStatusCode(400);
      response.setMessage(e.getMessage());
    }
    catch (Exception e){
      response.setStatusCode(500);
      response.setMessage("Error saving user to " + e.getMessage());
    }
    return response;
  }

  @Override
  public Response login(LoginDTO loginDTO) {
    Response response = new Response();

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
      var user = userRepository.findByEmail(loginDTO.getEmail())
          .orElseThrow(()->new BookingException("User does not exist"));
      var token = jwtService.generateToken(user);
      response.setStatusCode(200);
      response.setMessage("Successful");
      response.setToken(token);
      response.setRole(user.getRole());

    }catch (BookingException e){
      response.setStatusCode(400);
      response.setMessage(e.getMessage());
    }catch (Exception e){
      response.setStatusCode(500);
      response.setMessage("Error logging in " +e.getMessage());
    }
    return response;
  }

  @Override
  public Response deleteUser(String userId) {
    Response response = new Response();
    try{
      User user = userRepository.findById(Long.valueOf(userId))
          .orElseThrow(()->new BookingException("User does not exist"));
      userRepository.deleteById(Long.valueOf(userId));
      UserDTO userDTO = Utils.mapUserEntityTOUserDTO(user);
      response.setMessage("Successful");
      response.setStatusCode(200);
      response.setUser(userDTO);

    }catch (BookingException e){
      response.setMessage(response.getMessage());
      response.setStatusCode(400);
    }catch(Exception e){
      response.setMessage(response.getMessage());
      response.setStatusCode(500);
    }
    return response;
  }

  @Override
  public Response getAllUser() {
    Response response = new Response();
    try{
      List<User> userList = userRepository.findAll();
      List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);
      if(userList == null || userList.isEmpty()){
        response.setStatusCode(404);
        response.setMessage("No users found");
        return  response;
      }
      response.setUsers(userDTOList);
      response.setStatusCode(200);
      response.setMessage("Successful");
    }catch (BookingException e){
      response.setMessage(e.getMessage());
      response.setStatusCode(400);
    }catch (Exception e){
      response.setMessage("Error getting all users "+ " "+ e.getMessage());
      response.setStatusCode(500);
    }

    return  response;
  }

  @Override
  public Response getUserById(String userId) {
    Response response = new Response();
    try{
      User user = userRepository.findById(Long.valueOf(userId))
          .orElseThrow(()-> new BookingException("Could not find user"));
      UserDTO userDTO = Utils.mapUserEntityTOUserDTO(user);
      response.setMessage("Successful");
      response.setStatusCode(200);
      response.setUser(userDTO);

    }catch (BookingException e){
      response.setStatusCode(400);
      response.setMessage(e.getMessage());

    }catch (Exception e){
      response.setStatusCode(500);
      response.setMessage(e.getMessage());
    }
    return response;
  }

  @Override
  public Response getUserBookingHistory(String userId) {
    Response response = new Response();
    try{
      User user = userRepository.findById(Long.valueOf(userId))
          .orElseThrow(()-> new BookingException("Could not find user"));
      UserDTO userDTO = Utils.mapUserEntitytoUserDTOWithUserBookings(user);
      response.setMessage("Successful");
      response.setStatusCode(200);
      response.setUser(userDTO);

    }catch (BookingException e){
      response.setStatusCode(400);
      response.setMessage(e.getMessage());

    }catch (Exception e){
      response.setStatusCode(500);
      response.setMessage(e.getMessage());
    }
    return response;
  }

  @Override
  public Response getInfo(String email) {
   Response response = new Response();

   try {
     User user = userRepository.findByEmail(email)
         .orElseThrow(()-> new BookingException("User does not exist"));
     UserDTO userDTO = Utils.mapUserEntityTOUserDTO(user);
     response.setMessage("Successfull");
     response.setStatusCode(200);
     response.setUser(userDTO);

   }catch (BookingException e){
     response.setStatusCode(400);
     response.setMessage(e.getMessage());
   }

   return response;
  }
}
