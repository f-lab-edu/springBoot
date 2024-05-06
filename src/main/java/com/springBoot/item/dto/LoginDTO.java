package com.springBoot.item.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDTO {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

}
