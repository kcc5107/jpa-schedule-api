package com.example.jpascheduleapi.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    private final Long id;
    private final String username;
    private final String email;
    private final String password;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    public UserResponseDto(Long id, String username, String password, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
