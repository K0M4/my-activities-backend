package com.example.myactivities.service.impl;

import com.example.myactivities.entity.Activity;
import com.example.myactivities.entity.UserAccount;
import com.example.myactivities.repository.ActivityRepository;
import com.example.myactivities.service.ActivityService;
import com.example.myactivities.service.UserAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ActivityServiceImpl implements ActivityService {

  private final ActivityRepository activityRepository;
  private final UserAccountService userAccountService;

  @Override
  public List<Activity> getActivities() {
    UserAccount userAccount = userAccountService.getCurrentUser();
    return activityRepository.findByUserAccountId(userAccount.getId());
  }

  @Override
  @Transactional
  public Activity saveActivity(Activity activity) {
    UserAccount userAccount = userAccountService.getCurrentUser();
    activity.setUserAccount(userAccount);
    return activityRepository.save(activity);
  }

  @Override
  public Activity updateActivity(Activity activity) {
    UserAccount userAccount = userAccountService.getCurrentUser();
    Activity existingActivity =
        activityRepository.findByIdAndUserAccountId(activity.getId(), userAccount.getId());
    activity.setId(existingActivity.getId());
    return activityRepository.save(activity);
  }

  @Override
  @Transactional
  public void deleteActivity(Long id) {
    UserAccount userAccount = userAccountService.getCurrentUser();
    activityRepository.deleteActivityByIdAndUserAccountId(id, userAccount.getId());
  }
}
