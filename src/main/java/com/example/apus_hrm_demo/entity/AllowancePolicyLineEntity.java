package com.example.apus_hrm_demo.entity;

import com.example.apus_hrm_demo.util.enum_util.Cycle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="allowance_policy_line")
@Getter
@Setter
@NoArgsConstructor
public class AllowancePolicyLineEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long allowancePolicyId;

    private Long allowanceId;

    @Enumerated(EnumType.STRING)
    private Cycle cycle;

    private Double amount;
}
