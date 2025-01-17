package com.dawood.hotelbooking.security;

import com.dawood.hotelbooking.service.security.CustomUserDetailsService;
import com.dawood.hotelbooking.utils.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {

  private final JWTService jwtService;
  private final CustomUserDetailsService customUserDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String jwtToken;
    final String userEmail;

    if(authHeader == null || !authHeader.startsWith("Bearer ")){
      filterChain.doFilter(request,response);
      return;
    }

    jwtToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(jwtToken);

    if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
      UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

      if(jwtService.isTokenValid(jwtToken, userDetails)){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
            null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request,response);

  }
}
