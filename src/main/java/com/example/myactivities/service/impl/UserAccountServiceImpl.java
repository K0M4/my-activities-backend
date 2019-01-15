package com.example.myactivities.service.impl;

import com.example.myactivities.entity.UserAccount;
import com.example.myactivities.entity.UserAccountDetails;
import com.example.myactivities.repository.UserAccountRepository;
import com.example.myactivities.service.UserAccountService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

  private final PasswordEncoder passwordEncoder;
  private final UserAccountRepository userAccountRepository;
  private final MessageSource messageSource;

  public UserAccountServiceImpl(
      @Lazy PasswordEncoder passwordEncoder,
      UserAccountRepository userAccountRepository,
      MessageSource messageSource) {
    this.passwordEncoder = passwordEncoder;
    this.userAccountRepository = userAccountRepository;
    this.messageSource = messageSource;
  }

  @Override
  public Long getCurrentUserId() {
    return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public UserAccount saveUserAccount(UserAccount userAccount) throws IllegalArgumentException {
    if (userAccountRepository.findByUsername(userAccount.getUsername()) != null) {
      System.out.println(LocaleContextHolder.getLocale());
      throw new IllegalArgumentException(
          messageSource.getMessage("error.user.exists", null, LocaleContextHolder.getLocale()));
    } else {
      userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
      return userAccountRepository.save(userAccount);
    }
  }

  public UserDetails loadUserByUsername(String username) {

    UserAccount userAccount = userAccountRepository.findByUsername(username);
    if (userAccount == null) {
      throw new UsernameNotFoundException(username);
    }
    return new UserAccountDetails(userAccount);
  }
}
