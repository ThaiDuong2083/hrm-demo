package com.example.apus_hrm_demo.entity;

import com.example.apus_hrm_demo.util.enum_util.ApplicableType;
import com.example.apus_hrm_demo.util.enum_util.PolicyType;
import com.example.apus_hrm_demo.util.enum_util.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name ="allowance_policy")
@Getter
@Setter
public class AllowancePolicyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PolicyType type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "applicable_type")
    private ApplicableType applicableType;

    private String description;
}
