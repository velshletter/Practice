package com.modsen.user_service.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDto {

    @Size(min = 2, max = 50)
    private String username;

}
