package com.example.jpascheduleapi.controller;

import com.example.jpascheduleapi.dto.ScheduleRequestDto;
import com.example.jpascheduleapi.dto.ScheduleResponseDto;
import com.example.jpascheduleapi.service.ScheduleService;
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
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleRequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {

        return ResponseEntity.ok(scheduleService.findScheduleById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@Valid @PathVariable Long id
            , @RequestBody ScheduleRequestDto requestDto) {

        return ResponseEntity.ok(scheduleService.updateSchedule(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {

        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok().build();
    }
}
