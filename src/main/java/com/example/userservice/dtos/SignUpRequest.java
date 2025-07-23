package com.example.userservice.dtos;

import lombok.Data;

@Data
public class SignUpRequest {
    private String email;
    private String name;
    private String password;
}
