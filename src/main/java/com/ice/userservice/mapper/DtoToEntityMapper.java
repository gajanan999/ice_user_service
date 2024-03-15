package com.ice.userservice.mapper;

import com.ice.userservice.dto.UserDto;
import com.ice.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring")
public interface DtoToEntityMapper {

    DtoToEntityMapper INSTANCE = Mappers.getMapper(DtoToEntityMapper.class);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "emailId", target = "emailId")
    @Mapping(source = "mobileNumber", target = "mobileNumber")
    User userDtoToUser(UserDto userDto);

    List<User> userDtosToUsers(List<UserDto> userDtos);
}
