package com.example.jpascheduleapi.user.controller;

import com.example.jpascheduleapi.common.Const;
import com.example.jpascheduleapi.user.dto.LoginRequestDto;
import com.example.jpascheduleapi.user.dto.LoginUserDto;
import com.example.jpascheduleapi.user.dto.UserResponseDto;
import com.example.jpascheduleapi.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginUserDto> login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletRequest request) {

        UserResponseDto userIdResponseDto = userService.login(requestDto.getEmail(), requestDto.getPassword());
        UserResponseDto userDto = userService.findUserById(userIdResponseDto.getId());
        LoginUserDto loginUser = new LoginUserDto(userDto.getId(), userDto.getUsername(), userDto.getEmail());

        HttpSession session = request.getSession();
        session.setAttribute(Const.LOGIN_USER, loginUser);

        return ResponseEntity.ok(loginUser);
    }

}
