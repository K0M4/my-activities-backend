package com.example.myactivities.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Entity
public class Activity {

  @Id
  @GeneratedValue(generator = "activitySequenceGenerator")
  @GenericGenerator(
      name = "activitySequenceGenerator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
        @org.hibernate.annotations.Parameter(name = "sequence_name", value = "ACTIVITY_SEQ"),
        @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
        @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
      })
  private Long id;

  @Length(max = 255)
  @Column(nullable = false)
  private String title;

  @Column private Date date;

  @Length(max = 255)
  @Column private String location;

  @Length(max = 1000)
  @Column private String note;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_account_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  @JsonProperty("user_account_id")
  private UserAccount userAccount;
}
