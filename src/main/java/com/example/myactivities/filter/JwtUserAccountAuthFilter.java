package com.example.myactivities.filter;

import com.example.myactivities.config.JwtConfig;
import com.example.myactivities.dto.UserAccountDto;
import com.example.myactivities.entity.UserAccount;
import com.example.myactivities.entity.UserAccountDetails;
import com.example.myactivities.mapper.UserAccountMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtUserAccountAuthFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authManager;
  private final JwtConfig jwtConfig;

  public JwtUserAccountAuthFilter(AuthenticationManager authManager, JwtConfig jwtConfig) {
    this.authManager = authManager;
    this.jwtConfig = jwtConfig;
    this.setRequiresAuthenticationRequestMatcher(
        new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    try {
      UserAccount userAccount =
          UserAccountMapper.MAPPER.userAccountDtoToUserAccount(
              new ObjectMapper().readValue(request.getInputStream(), UserAccountDto.class));
      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken(
              userAccount.getUsername(), userAccount.getPassword(), Collections.emptyList());
      return authManager.authenticate(authToken);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication auth) {

    UserAccountDetails userAccountDetails = (UserAccountDetails) (auth.getPrincipal());
    long now = System.currentTimeMillis();
    String token =
        Jwts.builder()
            .setSubject(Long.toString(userAccountDetails.getUser().getId()))
            .claim(
                "authorities",
                auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()))
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + jwtConfig.getExpiration() * 1000)) // in milliseconds
            .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
            .compact();

    response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
    response.addHeader(
        "Jwt-Expiration",
        Long.toString(new Date(now + jwtConfig.getExpiration() * 1000).getTime()));
  }
}
