package com.example.apus_hrm_demo.entity;

import com.example.apus_hrm_demo.util.ApplicableType;
import com.example.apus_hrm_demo.util.PolicyType;
import com.example.apus_hrm_demo.util.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name ="reward_policy")
@Getter
@Setter
public class RewardPolicyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PolicyType type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date")
    private Long endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "applicable_type")
    private ApplicableType applicableType;

    private String description;

    @OneToMany(mappedBy = "rewardPolicyEntity", fetch = FetchType.LAZY)
    private Set<RewardPolicyApplicableTargetEntity> applicableTargets;
}
