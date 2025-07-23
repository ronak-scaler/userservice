package com.example.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Roles extends BaseModel{
    private String role;
    @Id
    private Long id;
}
