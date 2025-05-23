package com.example.jpascheduleapi.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserRequestDto {
    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    @NotBlank
    @Email
    private final String email;
}
