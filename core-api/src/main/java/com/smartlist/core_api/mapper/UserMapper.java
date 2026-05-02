package com.smartlist.core_api.mapper;

import com.smartlist.core_api.dto.UserDTO;
import com.smartlist.core_api.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);
}
