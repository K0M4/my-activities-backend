package com.example.myactivities.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class UserAccount {

  @Id
  @GeneratedValue(generator = "userAccountSequenceGenerator")
  @GenericGenerator(
      name = "userAccountSequenceGenerator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
        @org.hibernate.annotations.Parameter(name = "sequence_name", value = "USER_ACCOUNT_SEQ"),
        @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
        @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
      })
  private Long id;

  @Length(max = 255)
  @Column(nullable = false, unique = true)
  private String username;

  @Length(max = 255)
  @Column(nullable = false)
  private String password;
}
