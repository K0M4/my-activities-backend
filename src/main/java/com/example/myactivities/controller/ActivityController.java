package com.example.myactivities.controller;

import com.example.myactivities.dto.ActivityDto;
import com.example.myactivities.mapper.ActivityMapper;
import com.example.myactivities.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/activity")
public class ActivityController {

  private final ActivityService activityService;

  @GetMapping
  public ResponseEntity<List<ActivityDto>> getAllActivities() {
    return ResponseEntity.ok(
        activityService.getActivities().stream()
            .map(ActivityMapper.MAPPER::activityToActivityDto)
            .collect(Collectors.toList()));
  }

  @PostMapping
  public ResponseEntity<ActivityDto> addActivity(@RequestBody ActivityDto activityDto) {
    return ResponseEntity.ok(
        ActivityMapper.MAPPER.activityToActivityDto(
            activityService.saveActivity(
                ActivityMapper.MAPPER.activityDtoToActivity(activityDto))));
  }

  @PutMapping
  public ResponseEntity<ActivityDto> updateActivity(@RequestBody ActivityDto activityDto) {
    return ResponseEntity.ok(
        ActivityMapper.MAPPER.activityToActivityDto(
            activityService.updateActivity(
                ActivityMapper.MAPPER.activityDtoToActivity(activityDto))));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteActivity(@PathVariable Long id) {
    activityService.deleteActivity(id);
    return new ResponseEntity(HttpStatus.OK);
  }
}
