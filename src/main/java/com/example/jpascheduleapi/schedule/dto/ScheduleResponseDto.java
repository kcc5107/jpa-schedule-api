package com.example.jpascheduleapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleResponseDto {
    private final Long id;
    private final String username;
    private final String title;
    private final String contents;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    @Builder
    public ScheduleResponseDto(Long id, String username, String title, String contents, LocalDateTime createAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }

}
