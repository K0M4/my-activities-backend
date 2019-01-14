package com.example.myactivities.service;

import com.example.myactivities.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountService extends UserDetailsService {

  UserAccount getCurrentUser();

  UserAccount saveUserAccount(UserAccount userAccount) throws IllegalArgumentException;
}
