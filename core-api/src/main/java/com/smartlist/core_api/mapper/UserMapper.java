package com.smartlist.core_api.mapper;

import com.smartlist.core_api.dto.user.UserResponse;
import com.smartlist.core_api.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    User toEntity(UserResponse response);
}
