package com.example.jpascheduleapi.user.service;

import com.example.jpascheduleapi.user.dto.UserRequestDto;
import com.example.jpascheduleapi.user.dto.UserResponseDto;
import com.example.jpascheduleapi.user.entity.User;
import com.example.jpascheduleapi.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;



    public UserResponseDto createUser(UserRequestDto requestDto) {
        User user = new User(requestDto.getUsername(), requestDto.getPassword(), requestDto.getEmail());
        User savedUser = userRepository.save(user);

        return UserResponseDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .password(savedUser.getPassword())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public UserResponseDto findUserById(Long id) {
        User foundUser = userRepository.findUserByIdOrElseThrow(id);
        return UserResponseDto.builder()
                .username(foundUser.getUsername())
                .email(foundUser.getEmail())
                .createdAt(foundUser.getCreatedAt())
                .modifiedAt(foundUser.getModifiedAt())
                .build();
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User foundUser = userRepository.findUserByIdOrElseThrow(id);

        if (!requestDto.getPassword().equals(foundUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 불일치");
        }

        foundUser.updateUsername(requestDto.getUsername());
        foundUser.updateEmail(requestDto.getEmail());

        return UserResponseDto.builder()
                .username(foundUser.getUsername())
                .email(foundUser.getEmail())
                .createdAt(foundUser.getCreatedAt())
                .modifiedAt(foundUser.getModifiedAt())
                .build();
    }

    public void deleteUser(Long id) {
        User foundUser = userRepository.findUserByIdOrElseThrow(id);
        userRepository.delete(foundUser);
    }
}
