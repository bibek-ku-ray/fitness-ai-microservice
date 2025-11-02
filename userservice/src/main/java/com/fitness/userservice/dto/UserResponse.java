package com.fitness.userservice.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String id;

    private String email;

    private String firstname;

    private String lastname;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
