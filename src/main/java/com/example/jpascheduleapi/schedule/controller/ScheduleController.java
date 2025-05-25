package com.example.jpascheduleapi.schedule.controller;

import com.example.jpascheduleapi.schedule.dto.ScheduleRequestDto;
import com.example.jpascheduleapi.schedule.dto.ScheduleResponseDto;
import com.example.jpascheduleapi.schedule.service.ScheduleService;
import com.example.jpascheduleapi.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleRequestDto requestDto,
                                                              @SessionAttribute UserResponseDto loginUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(requestDto, loginUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.findScheduleById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id,
                                                              @Valid @RequestBody ScheduleRequestDto requestDto,
                                                              @SessionAttribute UserResponseDto loginUser) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, requestDto, loginUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id,
                                               @SessionAttribute UserResponseDto loginUser) {
        scheduleService.deleteSchedule(id, loginUser);
        return ResponseEntity.ok().build();
    }
}
