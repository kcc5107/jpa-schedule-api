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
    private final UserRepository userRepository;

    public UserResponseDto createUser(UserRequestDto requestDto) {
        // 이메일은 유니크 키 이므로 중복불가, 회원가입하려는 이메일이 이미 존재할 경우 409 상태코드 반환
        if (userRepository.existsUserByEmail(requestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "already exist email = " + requestDto.getEmail());
        }

        User user = new User(requestDto.getUsername(), requestDto.getPassword(), requestDto.getEmail());
        User savedUser = userRepository.save(user);

        // 생성시 수정시간은 생성시간과 동일하기때문에 modifiedAt은 반환안함
        return UserResponseDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .password(savedUser.getPassword())
                .email(savedUser.getEmail())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }

    public UserResponseDto findUserById(Long id) {
        User foundUser = userRepository.findUserByIdOrElseThrow(id);
        return UserResponseDto.builder()
                .id(foundUser.getId())
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

        // 이메일은 유니크 키 이므로 중복불가, 수정하려는 이메일이 이미 존재할 경우 409 상태코드 반환
        if (userRepository.existsUserByEmail(requestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "already exist email = " + requestDto.getEmail());
        }

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

    public UserResponseDto login(String email, String password) {
        User foundUser = userRepository.findUserByEmailAndPasswordOrElseThrow(email, password);
        return UserResponseDto.builder().id(foundUser.getId()).build();
    }
}
