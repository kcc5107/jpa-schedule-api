package com.example.jpascheduleapi.schedule.repository;

import com.example.jpascheduleapi.schedule.entity.Comment;
import com.example.jpascheduleapi.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findCommentByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    List<Comment> findAllByScheduleId(Long scheduleId);
}
