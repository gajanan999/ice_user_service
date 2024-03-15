package com.ice.userservice.controller;

import com.ice.userservice.constant.ApiStatus;
import com.ice.userservice.dto.RestResponse;
import com.ice.userservice.dto.UserDto;
import com.ice.userservice.exception.NoRecordFound;
import com.ice.userservice.exception.RecordAlreadyExistsException;
import com.ice.userservice.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        logger.debug("Got the request for /users getAllUsers");
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) {
        logger.debug("Got the request for /users createUser");
        try{
            UserDto userDtos = userService.createUser(userDto);
            return ResponseEntity.ok(userDtos);
        }catch(RecordAlreadyExistsException e){
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new RestResponse(null,e.getErrorCode(), e.getErrorMessage(), ApiStatus.FAILED.toString()));
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/users")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto) {
        logger.debug("Got the request for /users updateUser");
        try{
            UserDto userDtos = userService.updateUser(userDto);
            return ResponseEntity.ok(userDtos);
        }catch(NoRecordFound e){
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestResponse(null,e.getErrorCode(), e.getErrorMessage(), ApiStatus.FAILED.toString()));
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        logger.info("Got the request for /users deleteUser userId - {}", userId);
        try{
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        }catch(NoRecordFound e){
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestResponse(null,e.getErrorCode(), e.getErrorMessage(), ApiStatus.FAILED.toString()));
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
