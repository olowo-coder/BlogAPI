package com.example.blog.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UsersDto {

    @NotNull(message = "first Name cannot be null")
    @Size(min = 2, message = "first Name must not be less than 2")
    private String firstName;

    @NotNull(message = "last Name cannot be null")
    @Size(min = 2, message = "last Name must not be less than 2")
    private String lastName;

    @NotNull(message = "email cannot be null")
    @Email
    private String email;

    private String phone;

    @NotNull(message = "password cannot be null")
    @Size(min = 8, max = 16, message = "Password must not be less than eight character and not more than 16")
    private String password;

    private Boolean status;
}
