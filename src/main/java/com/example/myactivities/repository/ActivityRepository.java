package com.example.myactivities.repository;

import com.example.myactivities.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

  List<Activity> findByUserAccountId(Long userAccountId);

  Activity findByIdAndUserAccountId(Long id, Long userAccountId);

  void deleteActivityByIdAndUserAccountId(Long id, Long userAccountId);
}
