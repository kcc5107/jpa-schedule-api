package com.example.jpascheduleapi.schedule.service;

import com.example.jpascheduleapi.schedule.dto.ScheduleRequestDto;
import com.example.jpascheduleapi.schedule.dto.ScheduleResponseDto;
import com.example.jpascheduleapi.schedule.entity.Schedule;
import com.example.jpascheduleapi.schedule.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto.getUsername(), requestDto.getTitle(), requestDto.getContents());
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return ScheduleResponseDto.builder()
                .id(savedSchedule.getId())
                .username(savedSchedule.getUsername())
                .title(savedSchedule.getTitle())
                .contents(savedSchedule.getContents())
                .createAt(savedSchedule.getCreatedAt())
                .build();
    }

    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule foundSchedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        return ScheduleResponseDto.builder()
                .username(foundSchedule.getUsername())
                .title(foundSchedule.getTitle())
                .contents(foundSchedule.getContents())
                .createAt(foundSchedule.getCreatedAt())
                .modifiedAt(foundSchedule.getModifiedAt())
                .build();
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule foundSchedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        foundSchedule.updateTitle(requestDto.getTitle());
        foundSchedule.updateContents(requestDto.getContents());

        return ScheduleResponseDto.builder()
                .username(foundSchedule.getUsername())
                .title(foundSchedule.getTitle())
                .contents(foundSchedule.getContents())
                .modifiedAt(foundSchedule.getModifiedAt())
                .build();
    }

    public void deleteSchedule(Long id) {
        Schedule foundSchedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        scheduleRepository.delete(foundSchedule);
    }
}
