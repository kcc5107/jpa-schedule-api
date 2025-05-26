package com.example.jpascheduleapi.schedule.controller;

import com.example.jpascheduleapi.common.Const;
import com.example.jpascheduleapi.schedule.dto.ScheduleRequestDto;
import com.example.jpascheduleapi.schedule.dto.ScheduleResponseDto;
import com.example.jpascheduleapi.schedule.entity.Schedule;
import com.example.jpascheduleapi.schedule.service.ScheduleService;
import com.example.jpascheduleapi.user.dto.LoginUserDto;
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
                                                              @SessionAttribute(name = Const.LOGIN_USER) LoginUserDto loginUser) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.createSchedule(requestDto, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(requestDto, loginUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.findScheduleById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id,
                                                              @Valid @RequestBody ScheduleRequestDto requestDto,
                                                              @SessionAttribute LoginUserDto loginUser) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, requestDto, loginUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id,
                                               @SessionAttribute LoginUserDto loginUser) {
        scheduleService.deleteSchedule(id, loginUser);
        return ResponseEntity.ok().build();
    }
}
