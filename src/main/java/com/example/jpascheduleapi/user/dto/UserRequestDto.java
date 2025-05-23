package com.example.jpascheduleapi.schedule.dto;

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
    @Email
    private final String email;
}
