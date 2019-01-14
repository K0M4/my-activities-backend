package com.example.myactivities.mapper;

import com.example.myactivities.dto.ActivityDto;
import com.example.myactivities.entity.Activity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ActivityMapper {

  ActivityMapper MAPPER = Mappers.getMapper(ActivityMapper.class);

  @Mapping(source = "userAccount.id", target = "userId")
  ActivityDto activityToActivityDto(Activity activity);

  @Mapping(source = "userId", target = "userAccount.id")
  Activity activityDtoToActivity(ActivityDto activity);
}
