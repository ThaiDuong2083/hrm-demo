package com.example.apus_hrm_demo.entity;

import com.example.apus_hrm_demo.util.AmountItem;
import com.example.apus_hrm_demo.util.Cycle;
import com.example.apus_hrm_demo.util.PayrollLineType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payroll_line")
@Setter
@Getter
public class PayrollLineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "payroll_id")
    private PayrollEntity payrollEntity;
    @Column(nullable = false,name = "group_target_id")
    private Long groupTargetId;
    @Column(nullable = false,name = "target_id")
    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayrollLineType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "amount_item")
    private AmountItem amountItem;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false, name = "taxable_amount")
    private Double taxableAmount;
    @Column(nullable = false, name = "insurance_amount")
    private Double insuranceAmount;
}
