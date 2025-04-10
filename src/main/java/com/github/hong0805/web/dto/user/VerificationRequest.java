package com.github.hong0805.web.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationRequest {
    private String userName;
    private String userID;
    private String userEmail;
}