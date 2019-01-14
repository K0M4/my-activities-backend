package com.example.myactivities.dto;

import com.example.myactivities.entity.Activity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDto {

  private Long id;
  private String email;
  private String password;
  private List<Activity> activities;
}
