package com.example.jpascheduleapi.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@AllArgsConstructor
@Getter
public class UserRequestDto {
    @NotBlank
    @Size(min = 2, max = 4, message = "2글자 이상, 4글자 이내")
    private final String username;

    @NotBlank
    @Size(min = 4, max = 10, message = "4글자 이상, 10글자 이내")
    private final String password;

    @NotBlank
    @Email
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "올바른 이메일 형식이 아닙니다."
    )
    private final String email;
}
