package com.example.apus_hrm_demo.entity;

import com.example.apus_hrm_demo.util.enum_util.AmountItem;
import com.example.apus_hrm_demo.util.enum_util.PayrollLineType;
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

    private Long payrollId;
    private Long groupTargetId;
    private Long targetId;

    @Enumerated(EnumType.STRING)
    private PayrollLineType type;

    @Enumerated(EnumType.STRING)
    private AmountItem amountItem;

    private Double amount;

    private Double taxableAmount;
    private Double insuranceAmount;
}
