package com.example.jpascheduleapi.schedule.service;

import com.example.jpascheduleapi.schedule.dto.CommentRequestDto;
import com.example.jpascheduleapi.schedule.dto.CommentResponseDto;
import com.example.jpascheduleapi.schedule.entity.Comment;
import com.example.jpascheduleapi.schedule.entity.Schedule;
import com.example.jpascheduleapi.schedule.repository.CommentRepository;
import com.example.jpascheduleapi.schedule.repository.ScheduleRepository;
import com.example.jpascheduleapi.user.dto.LoginUserDto;
import com.example.jpascheduleapi.user.entity.User;
import com.example.jpascheduleapi.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto, LoginUserDto loginUser, Long scheduleId) {
        User user = userRepository.findUserByIdOrElseThrow(loginUser.getId());
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(scheduleId);

        Comment comment = new Comment(requestDto.getContents());
        comment.setUser(user);
        comment.setSchedule(schedule);

        commentRepository.save(comment);

        return CommentResponseDto.builder()
                .contents(comment.getContents())
                .username(user.getUsername())
                .scheduleTitle(schedule.getTitle())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    @Transactional
    public CommentResponseDto findCommentById(Long id) {
        Comment foundComment = commentRepository.findCommentByIdOrElseThrow(id);
        User user = foundComment.getUser();
        Schedule schedule = foundComment.getSchedule();

        return CommentResponseDto.builder()
                .contents(foundComment.getContents())
                .username(user.getUsername())
                .scheduleTitle(schedule.getTitle())
                .createdAt(foundComment.getCreatedAt())
                .modifiedAt(foundComment.getModifiedAt())
                .build();
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, LoginUserDto loginUser) {
        Comment foundComment = commentRepository.findCommentByIdOrElseThrow(id);

        if (!foundComment.getUser().getId().equals(loginUser.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정은 작성자만 가능합니다.");
        }

        foundComment.updateContents(requestDto.getContents());

        return CommentResponseDto.builder()
                .contents(foundComment.getContents())
                .modifiedAt(foundComment.getModifiedAt())
                .build();
    }

    public void deleteComment(Long id, LoginUserDto loginUser) {
        Comment foundComment = commentRepository.findCommentByIdOrElseThrow(id);

        if (!foundComment.getUser().getId().equals(loginUser.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "삭제는 작성자만 가능합니다.");
        }

        commentRepository.delete(foundComment);
    }
}
