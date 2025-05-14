package com.example.apus_hrm_demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="allowance_policy_applicable_target")
@Getter
@Setter
public class AllowancePolicyApplicableTargetEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allownce_policy_id",nullable = false)
    private AllowancePolicyEntity allowancePolicyEntity;

    @Column(name = "target_id",nullable = false)
    private Long targetId;
}
