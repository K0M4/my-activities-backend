package com.example.myactivities.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.data.mapping.Alias.ofNullable;

public class CorsFilter extends OncePerRequestFilter {

  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    final String origin = request.getHeader("Origin");

    if (ofNullable(origin).isPresent() && origin.equals("http://localhost:4200")) {
      response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
      response.addHeader("Access-Control-Allow-Credentials", "true");
      response.setHeader("Access-Control-Expose-Headers", "Authorization, Jwt-Expiration");
      if (request.getHeader("Access-Control-Request-Method") != null
          && "OPTIONS".equals(request.getMethod())) {
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader(
            "Access-Control-Allow-Headers",
            "X-Requested-With,Origin,Content-Type,Accept,Authorization,Access-Control-Request-Headers,Access-Control-Request-Method");
        response.setStatus(200);
      }
    }
    chain.doFilter(request, response);
  }
}
