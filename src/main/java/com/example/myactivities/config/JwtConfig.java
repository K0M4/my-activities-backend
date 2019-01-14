package com.example.myactivities.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
  private String uri;
  private String header;
  private String prefix;
  private int expiration;
  private String secret;
}
