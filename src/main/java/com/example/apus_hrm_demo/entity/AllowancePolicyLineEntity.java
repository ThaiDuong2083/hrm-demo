package com.example.apus_hrm_demo.entity;

import com.example.apus_hrm_demo.util.Cycle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="allowance_policy")
@Getter
@Setter
public class AllowancePolicyLineEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allownce_policy_id")
    private AllowancePolicyEntity allowancePolicyEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "allowance_id")
    private AllowanceEntity allowanceEntity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cycle cycle;

    @Column(nullable = false)
    private Double amount;
}
