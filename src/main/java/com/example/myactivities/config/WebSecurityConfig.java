package com.example.myactivities.config;

import com.example.myactivities.filter.CorsFilter;
import com.example.myactivities.filter.JwtAuthFilter;
import com.example.myactivities.filter.JwtUserAccountAuthFilter;
import com.example.myactivities.service.UserAccountService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtConfig.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private JwtConfig jwtConfig;
  private UserAccountService userAccountService;
  private PasswordEncoder encoder;

  public WebSecurityConfig(
      JwtConfig jwtConfig, UserAccountService userAccountService, @Lazy PasswordEncoder encoder) {
    this.jwtConfig = jwtConfig;
    this.userAccountService = userAccountService;
    this.encoder = encoder;
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(
            (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
        .and()
        .userDetailsService(userAccountService)
        .addFilter(new JwtUserAccountAuthFilter(authenticationManager(), jwtConfig))
        .addFilterBefore(new CorsFilter(), JwtUserAccountAuthFilter.class)
        .addFilterAfter(new JwtAuthFilter(jwtConfig), CorsFilter.class)
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS, "/**")
        .permitAll()
        .antMatchers(jwtConfig.getUri(), "/user-account")
        .permitAll()
        .anyRequest()
        .authenticated();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userAccountService).passwordEncoder(encoder);
  }
}
