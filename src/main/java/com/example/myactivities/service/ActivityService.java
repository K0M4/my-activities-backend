package com.example.myactivities.service;

import com.example.myactivities.entity.Activity;

import java.util.List;

public interface ActivityService {

  List<Activity> getActivities();

  Activity saveActivity(Activity activity);

  Activity updateActivity(Activity activity);

  void deleteActivity(Long id);
}
