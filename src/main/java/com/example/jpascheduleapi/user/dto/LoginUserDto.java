package com.example.jpascheduleapi.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginUserDto {
    private final Long id;
    private final String username;
    private final String email;
}
