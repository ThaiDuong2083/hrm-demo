package com.example.apus_hrm_demo.entity;

import com.example.apus_hrm_demo.util.Cycle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="allowance_policy_line")
@Getter
@Setter
public class AllowancePolicyLineEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "allownce_policy_id", nullable = false)
    private Long allowancePolicyId;

    @Column(nullable = false, name = "allowance_id")
    private Long allowanceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cycle cycle;

    @Column(nullable = false)
    private Double amount;
}
