package com.example.indie91.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Document(collection = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    @NotBlank
    private String name;
    private String phone;

    @Email
    private String email;
    private String firebaseUid;

    private boolean isStaff;
    private boolean isSuperuser;
    private boolean isActive;

    private LocalDateTime joinedOn;
    private String avatarUrl;
}
