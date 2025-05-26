package com.example.jpascheduleapi.user.controller;

import com.example.jpascheduleapi.schedule.dto.ScheduleResponseDto;
import com.example.jpascheduleapi.schedule.service.ScheduleService;
import com.example.jpascheduleapi.user.dto.UserRequestDto;
import com.example.jpascheduleapi.user.dto.UserResponseDto;
import com.example.jpascheduleapi.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id
            , @Valid @RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(userService.updateUser(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/schedules")
    public ResponseEntity<List<ScheduleResponseDto>> findSchedulesByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.findSchedulesByUserId(id));
    }
}
