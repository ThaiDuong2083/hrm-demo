package com.example.apus_hrm_demo.entity;

import com.example.apus_hrm_demo.util.enum_util.Cycle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="reward_policy_line")
@Getter
@Setter
@NoArgsConstructor
public class RewardPolicyLineEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "reward_policy_id")
    private Long rewardPolicyId;

    @Column(nullable = false, name = "reward_id")
    private Long rewardId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cycle cycle;

    @Column(nullable = false)
    private Double amount;

    public RewardPolicyLineEntity(Long id) {
        this.id = id;
    }
}
