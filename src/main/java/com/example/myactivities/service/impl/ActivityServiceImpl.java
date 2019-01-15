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
    return activityRepository.findByUserAccountId(userAccountService.getCurrentUserId());
  }

  @Override
  @Transactional
  public Activity saveActivity(Activity activity) {
    UserAccount userAccount = new UserAccount();
    userAccount.setId(userAccountService.getCurrentUserId());
    activity.setUserAccount(userAccount);
    return activityRepository.save(activity);
  }

  @Override
  public Activity updateActivity(Activity activity) {
    Activity existingActivity =
        activityRepository.findByIdAndUserAccountId(
            activity.getId(), userAccountService.getCurrentUserId());
    activity.setId(existingActivity.getId());
    return activityRepository.save(activity);
  }

  @Override
  @Transactional
  public void deleteActivity(Long id) {
    activityRepository.deleteActivityByIdAndUserAccountId(
        id, userAccountService.getCurrentUserId());
  }
}
