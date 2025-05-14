package com.example.apus_hrm_demo.entity;

import com.example.apus_hrm_demo.util.Cycle;
import com.example.apus_hrm_demo.util.PolicyType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "payroll")
@Setter
@Getter
public class PayrollEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "employee_id")
    private Long employeeId;
    @Column(nullable = false,name = "department_id")
    private Long departmentId;
    @Column(nullable = false,name = "position_id")
    private Long positionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PolicyType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cycle cycle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(nullable = false,name = "start_date")
    private LocalDate startDate;

    @Column(name = "total_allowance_amount",nullable = false)
    private Double totalAllowanceAmount;

    private String note;

    @OneToMany(mappedBy = "payrollEntity",fetch = FetchType.LAZY)
    private Set<PayrollLineEntity> payrollLineEntitySet;
}
