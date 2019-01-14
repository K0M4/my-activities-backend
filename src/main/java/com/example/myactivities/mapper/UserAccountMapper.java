package com.example.myactivities.mapper;

import com.example.myactivities.dto.UserAccountDto;
import com.example.myactivities.entity.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserAccountMapper {

  UserAccountMapper MAPPER = Mappers.getMapper(UserAccountMapper.class);

  @Mapping(source = "username", target = "email")
  UserAccountDto userAccountToUserAccountDto(UserAccount userAccount);

  @Mapping(source = "email", target = "username")
  UserAccount userAccountDtoToUserAccount(UserAccountDto userAccount);
}
