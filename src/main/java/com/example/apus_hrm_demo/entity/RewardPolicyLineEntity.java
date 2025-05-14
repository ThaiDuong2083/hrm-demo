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

    @Column(nullable = false, name = "reward_policy_id")
    private Long rewardPolicyLineId;

    @Column(nullable = false, name = "reward_id")
    private Long rewardId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cycle cycle;

    @Column(nullable = false)
    private Double amount;
}
