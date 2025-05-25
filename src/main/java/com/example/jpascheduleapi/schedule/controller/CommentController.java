package com.example.jpascheduleapi.schedule.controller;

import com.example.jpascheduleapi.common.Const;
import com.example.jpascheduleapi.schedule.dto.CommentRequestDto;
import com.example.jpascheduleapi.schedule.dto.CommentResponseDto;
import com.example.jpascheduleapi.schedule.service.CommentService;
import com.example.jpascheduleapi.user.dto.LoginUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/schedules/{schedule_id}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> crateComment(@Valid @RequestBody CommentRequestDto requestDto,
                                                           @SessionAttribute(name = Const.LOGIN_USER) LoginUserDto loginUser,
                                                           @PathVariable(name = "schedule_id") Long scheduleId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(requestDto, loginUser, scheduleId));
    }

    @GetMapping("/{comment_id}")
    public ResponseEntity<CommentResponseDto> findCommentById(@PathVariable(name = "comment_id") Long commentId) {
        return ResponseEntity.ok(commentService.findCommentById(commentId));
    }

    @PatchMapping("/{comment_id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable(name = "comment_id") Long commentId,
                                                            @Valid @RequestBody CommentRequestDto requestDto,
                                                            @SessionAttribute(name = Const.LOGIN_USER) LoginUserDto loginUser) {
        return ResponseEntity.ok(commentService.updateComment(commentId, requestDto, loginUser));
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "comment_id") Long commentId,
                                              @SessionAttribute(name = Const.LOGIN_USER) LoginUserDto loginUser) {
        commentService.deleteComment(commentId, loginUser);
        return ResponseEntity.ok().build();
    }

}
