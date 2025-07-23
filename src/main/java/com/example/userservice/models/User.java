package com.example.userservice.models;

import jakarta.persistence.Entity;
import lombok.Data;
import org.hibernate.annotations.ManyToAny;

import java.util.List;

@Entity
@Data
public class User extends BaseModel {
    private String name;
    private String email;
    private String hashedPassword;

    @ManyToAny
    private List<Roles> roles;
    private boolean isVerified;
}
