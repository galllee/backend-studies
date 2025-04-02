package com.example.miniproject.domain.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDate hireDate;
    private LocalDate birthDate;

    //@ManyToOne(cascade = )
    private Long teamId;
    //일단 이렇게하고 그 객체끼리 협업하게 하는기능은 필요하면 다시 하기
    //그걸 위해서 연관관계 매핑이 필요한거맞나? 외래키를 참조한다고
    //객체에서 연관관계 매핑을 꼭 해줄필요는 없는거같은데

    @Builder
    public Member(String name, Role role, LocalDate hireDate, LocalDate birthDate, Long teamId) {
        this.name = name;
        this.role = role;
        this.hireDate = hireDate;
        this.birthDate = birthDate;
        this.teamId = teamId;
    }
}
