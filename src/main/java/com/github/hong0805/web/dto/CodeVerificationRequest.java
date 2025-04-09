package com.github.hong0805.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeVerificationRequest {
    private String userEmail;
    private String code;
}
