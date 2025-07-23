package com.example.userservice.dtos;

import com.example.userservice.models.Token;
import lombok.Data;

import java.util.Date;


@Data
public class LoginDto {
    private Date expiry;
    private boolean active;

    private String value;

    public static LoginDto from(Token t){

        LoginDto loginDto = new LoginDto();
        loginDto.setExpiry(t.getExpiry());
        loginDto.setActive(t.isActive());
        loginDto.setValue(t.getValue());
        return loginDto;
    }
}