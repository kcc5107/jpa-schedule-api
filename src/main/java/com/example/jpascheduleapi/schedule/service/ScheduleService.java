package com.example.jpascheduleapi.schedule.service;

import com.example.jpascheduleapi.schedule.dto.ScheduleAuthorDto;
import com.example.jpascheduleapi.schedule.dto.ScheduleRequestDto;
import com.example.jpascheduleapi.schedule.dto.ScheduleResponseDto;
import com.example.jpascheduleapi.schedule.entity.Schedule;
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
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto, LoginUserDto loginUser) {
        User user = userRepository.findUserByIdOrElseThrow(loginUser.getId());

        Schedule schedule = new Schedule(requestDto.getTitle(), requestDto.getContents());
        schedule.setUser(user);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return ScheduleResponseDto.builder()
                .id(savedSchedule.getId())
                // ScheduleAuthorDto는 String username 필드를 가지고 있음 (확장과 유지보수를 위해 dto사용)
                .user(new ScheduleAuthorDto(savedSchedule.getUser().getUsername()))
                .title(savedSchedule.getTitle())
                .contents(savedSchedule.getContents())
                .createAt(savedSchedule.getCreatedAt())
                .build();
    }

    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule foundSchedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        User user = foundSchedule.getUser();

        return ScheduleResponseDto.builder()
                .user(new ScheduleAuthorDto(user.getUsername()))
                .title(foundSchedule.getTitle())
                .contents(foundSchedule.getContents())
                .createAt(foundSchedule.getCreatedAt())
                .modifiedAt(foundSchedule.getModifiedAt())
                .build();
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto, LoginUserDto loginUser) {
        Schedule foundSchedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        //todo 이 검증도 반복된다면 필터 사용 가능한지 확인
        if (!foundSchedule.getUser().getId().equals(loginUser.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정은 작성자만 가능합니다.");
        }

        foundSchedule.updateTitle(requestDto.getTitle());
        foundSchedule.updateContents(requestDto.getContents());

        return ScheduleResponseDto.builder()
                .title(foundSchedule.getTitle())
                .contents(foundSchedule.getContents())
                .modifiedAt(foundSchedule.getModifiedAt())
                .build();
    }

    public void deleteSchedule(Long id, LoginUserDto loginUser) {
        Schedule foundSchedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        if (!foundSchedule.getUser().getId().equals(loginUser.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "삭제는 작성자만 가능합니다.");
        }

        scheduleRepository.delete(foundSchedule);
    }
}
