package com.example.jpascheduleapi.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {
    @NotBlank
    @Size(max = 20)
    private final String title;

    @NotBlank
    private final String contents;
}
