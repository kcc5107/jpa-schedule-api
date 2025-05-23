package com.example.jpascheduleapi.schedule.service;

import com.example.jpascheduleapi.schedule.dto.UserRequestDto;
import com.example.jpascheduleapi.schedule.dto.UserResponseDto;
import com.example.jpascheduleapi.schedule.entity.User;
import com.example.jpascheduleapi.schedule.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;



    public UserResponseDto createUser(UserRequestDto requestDto) {
        User user = new User(requestDto.getUsername(), requestDto.getEmail());
        User savedUser = userRepository.save(user);

        return UserResponseDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
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
