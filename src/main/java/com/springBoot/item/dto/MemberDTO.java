package com.springBoot.item.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberDTO {

    private Long memberId;

    @NotEmpty
    private String loginId;

//    @NotEmpty
    private String address;

//    @NotEmpty
    private String email;

//    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

//    @NotEmpty
    private String role;

}
