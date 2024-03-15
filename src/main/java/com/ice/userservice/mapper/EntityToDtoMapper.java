package com.ice.userservice.mapper;

import com.ice.userservice.dto.UserDto;
import com.ice.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntityToDtoMapper {

    EntityToDtoMapper INSTANCE = Mappers.getMapper(EntityToDtoMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "emailId", target = "emailId")
    @Mapping(source = "mobileNumber", target = "mobileNumber")
    UserDto userToUserDto(User user);

    List<UserDto> usersToUserDtos(List<User> users);

}
