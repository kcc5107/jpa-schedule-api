package com.example.jpascheduleapi.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {
    @NotBlank
    @Range(max = 20)
    private final String title;

    @NotBlank
    private final String contents;
}
