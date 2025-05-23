package com.example.apus_hrm_demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="reward_policy_applicable_target")
@Getter
@Setter
public class RewardPolicyApplicableTargetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long rewardPolicyId;

    private Long targetId;
}
