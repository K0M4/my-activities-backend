package com.example.myactivities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDto {

  private Long id;
  private String title;
  private Date date;
  private String location;
  private String note;
  private Long userId;
}
