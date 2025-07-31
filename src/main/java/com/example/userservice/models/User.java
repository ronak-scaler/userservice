package com.example.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import org.hibernate.annotations.ManyToAny;

import java.util.List;

@Entity
@Data
public class User extends BaseModel {
    private String name;
    private String email;

    private String hashedPassword;

    @ManyToMany
    private List<Roles> roles;

    private boolean isVerified;
}