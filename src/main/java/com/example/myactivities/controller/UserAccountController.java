package com.example.myactivities.controller;

import com.example.myactivities.dto.UserAccountDto;
import com.example.myactivities.mapper.UserAccountMapper;
import com.example.myactivities.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-account")
public class UserAccountController {

  private final UserAccountService userAccountService;

  @PostMapping
  public ResponseEntity<UserAccountDto> addUser(@RequestBody UserAccountDto userAccountDto) {
    return ResponseEntity.ok(
        UserAccountMapper.MAPPER.userAccountToUserAccountDto(
            userAccountService.saveUserAccount(
                UserAccountMapper.MAPPER.userAccountDtoToUserAccount(userAccountDto))));
  }
}
