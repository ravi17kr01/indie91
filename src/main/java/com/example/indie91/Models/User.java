package com.example.indie91.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "app_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    private String name;

    private String phone;

    private String email;

    @Column(name = "firebase_uid")
    private String firebaseUid;

    @Column(name = "is_staff")
    private boolean isStaff;

    @Column(name = "is_superuser")
    private boolean isSuperuser;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "joined_on")
    private LocalDateTime joinedOn;

    @Column(name = "avatar_url")
    private String avatarUrl;
}
