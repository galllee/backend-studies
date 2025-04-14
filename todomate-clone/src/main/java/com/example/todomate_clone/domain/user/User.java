package com.example.todomate_clone.domain.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //기본값 me
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    private boolean agreeToMarketing;

    private String introduction;
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public User(String name, String email, String password, boolean agreeToMarketing, Gender gender, LocalDate birthDate, String profileImage) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.agreeToMarketing = agreeToMarketing;
        this.gender = gender;
        this.birthDate = birthDate;
        this.profileImage = profileImage;
    }
}
