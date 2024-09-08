package com.dawood.hotelbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_user")
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Long id;
  @NotBlank(message = "Email is required")
  @Column(unique = true)
  @Email
  private String email;
  @NotBlank(message = "Fullname is required")
  private String fullname;
  @NotBlank(message = "Password is required")
  private String password;
  private String phoneNumber;
  private String role;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<Booking> bookings;

  public Collection<? extends GrantedAuthority> getAuthorities(){
    return List.of(new SimpleGrantedAuthority((role)));
  }

  public String getUsername(){
    return  this.email;
  }

}
