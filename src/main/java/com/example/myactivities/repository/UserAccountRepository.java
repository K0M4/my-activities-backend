package com.example.myactivities.repository;

import com.example.myactivities.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

  UserAccount findByUsername(String username);
}
