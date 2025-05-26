package com.example.jpascheduleapi.schedule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDto {
    private final String contents;
    private final String username;
    private final String scheduleTitle;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    public CommentResponseDto(String scheduleTitle, String username, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.contents = contents;
        this.username = username;
        this.scheduleTitle = scheduleTitle;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
