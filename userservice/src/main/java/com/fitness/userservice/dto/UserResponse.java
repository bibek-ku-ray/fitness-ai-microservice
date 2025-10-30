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
    private UUID id;

    private String email;

    private String password;

    private String firstname;

    private String lastname;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
