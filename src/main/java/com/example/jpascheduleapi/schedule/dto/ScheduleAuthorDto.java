package com.example.jpascheduleapi.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleAuthorDto {
    private final String username;

    public ScheduleAuthorDto(String username) {
        this.username = username;
    }
}
