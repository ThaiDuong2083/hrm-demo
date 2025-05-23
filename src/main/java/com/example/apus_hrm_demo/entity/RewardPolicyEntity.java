package com.example.apus_hrm_demo.entity;

import com.example.apus_hrm_demo.util.enum_util.ApplicableType;
import com.example.apus_hrm_demo.util.enum_util.PolicyType;
import com.example.apus_hrm_demo.util.enum_util.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name ="reward_policy")
@Getter
@Setter
public class RewardPolicyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    @Enumerated(EnumType.STRING)
    private PolicyType type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private ApplicableType applicableType;

    private String description;

}
