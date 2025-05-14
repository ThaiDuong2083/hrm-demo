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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reward_policy_id",nullable = false)
    private RewardPolicyEntity rewardPolicyEntity;

    @Column(name = "target_id",nullable = false)
    private Long targetId;
}
