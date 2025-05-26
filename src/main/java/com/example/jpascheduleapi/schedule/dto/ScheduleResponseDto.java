package com.example.jpascheduleapi.schedule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleResponseDto {
    private final Long id;
    private final ScheduleAuthorDto user;
    private final String title;
    private final String contents;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    @Builder
    public ScheduleResponseDto(Long id, ScheduleAuthorDto user, String title, String contents, LocalDateTime createAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }
}
