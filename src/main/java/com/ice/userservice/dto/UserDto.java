package com.ice.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Data
@Setter
@Getter
@ToString
public class UserDto {

    private UUID id;

    private String userName;

    private String password;

    @Email(message = "Invalid email format")
    private String emailId;

    @Size(min = 10, max = 12, message = "Mobile number must be between 10 and 12 digits long")
    private String mobileNumber;

}
