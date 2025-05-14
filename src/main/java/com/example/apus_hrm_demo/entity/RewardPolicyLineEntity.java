package com.example.apus_hrm_demo.entity;

import com.example.apus_hrm_demo.util.Cycle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="allowance_policy")
@Getter
@Setter
public class RewardPolicyLineEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reward_policy_id")
    private RewardPolicyLineEntity rewardPolicyLineEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "reward_id")
    private RewardEntity rewardEntity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cycle cycle;

    @Column(nullable = false)
    private Double amount;
}
