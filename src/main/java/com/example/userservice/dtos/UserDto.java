package com.example.userservice.dtos;

import com.example.userservice.models.Roles;
import com.example.userservice.models.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String name;
    private String email;
    private List<Roles> roles;

    public static UserDto from(User u){
      UserDto userDto = new UserDto();
      userDto.setEmail(u.getEmail());
      userDto.setName(u.getName());
      userDto.setRoles(u.getRoles());
      return userDto;
    }
}
