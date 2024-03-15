package com.ice.userservice.service;

import com.ice.userservice.constant.ErrorCodeConstant;
import com.ice.userservice.controller.UserController;
import com.ice.userservice.dao.UserRepository;
import com.ice.userservice.dto.UserDto;
import com.ice.userservice.entity.User;
import com.ice.userservice.exception.NoRecordFound;
import com.ice.userservice.exception.RecordAlreadyExistsException;
import com.ice.userservice.mapper.DtoToEntityMapper;
import com.ice.userservice.mapper.EntityToDtoMapper;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return EntityToDtoMapper.INSTANCE.usersToUserDtos(users);
    }

    public UserDto createUser(UserDto userDto) throws RecordAlreadyExistsException {
        User user = DtoToEntityMapper.INSTANCE.userDtoToUser(userDto);

        // Check if a user with the same username already exists
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new RecordAlreadyExistsException(ErrorCodeConstant.ERR_102,messageSource.getMessage(ErrorCodeConstant.ERR_102,null, Locale.ENGLISH));
        }
        // Check if a user with the same email  already exists
        if (userRepository.existsByEmailId(user.getEmailId())) {
            throw new RecordAlreadyExistsException(ErrorCodeConstant.ERR_103,messageSource.getMessage(ErrorCodeConstant.ERR_103,null, Locale.ENGLISH));
        }
        user = userRepository.save(user);
        UserDto createdUserDto = EntityToDtoMapper.INSTANCE.userToUserDto(user);
        return createdUserDto;
    }

    public UserDto updateUser(UserDto userDto) throws NoRecordFound {
        Optional<User> existUser = userRepository.findById(userDto.getId());

        if(existUser.isPresent()){
            User user = DtoToEntityMapper.INSTANCE.userDtoToUser(userDto);
            userRepository.save(user);

        }else{
            throw new NoRecordFound(ErrorCodeConstant.ERR_101,messageSource.getMessage(ErrorCodeConstant.ERR_101,null, Locale.ENGLISH));
        }
        Optional<User> modifiedUser = userRepository.findById(userDto.getId());
        UserDto modifiedUserDto = EntityToDtoMapper.INSTANCE.userToUserDto(modifiedUser.get());
        return modifiedUserDto;
    }

    public void deleteUser(UUID userId) throws NoRecordFound {
        Optional<User> existUser = userRepository.findById(userId);
        if(!existUser.isPresent()){
            throw new NoRecordFound(ErrorCodeConstant.ERR_101,messageSource.getMessage(ErrorCodeConstant.ERR_101,null, Locale.ENGLISH));
        }else{
            userRepository.deleteById(userId);
        }
    }
}
