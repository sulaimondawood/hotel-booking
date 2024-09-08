package com.dawood.hotelbooking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginDTO {
  @NotBlank(message = "Email is required")
  private String email;
  @NotBlank(message = "Password is required")
  private String password;
}
