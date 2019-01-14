package com.example.myactivities.service.impl;

import com.example.myactivities.entity.UserAccount;
import com.example.myactivities.repository.UserAccountRepository;
import com.example.myactivities.service.UserAccountService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
  public UserAccount getCurrentUser() {
    String username =
        (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userAccountRepository.findByUsername(username);
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

  private class UserAccountDetails implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    private final UserAccount user;
    private final Collection<? extends GrantedAuthority> authorities;

    UserAccountDetails(UserAccount user) {
      this.user = user;
      this.authorities = getAuthorities();
    }

    @Override
    public String getUsername() {
      return user.getUsername();
    }

    @Override
    public String getPassword() {
      return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      final List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
      return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    @Override
    public boolean isAccountNonLocked() {
      return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }

    @Override
    public boolean isEnabled() {
      return true;
    }
  }
}
