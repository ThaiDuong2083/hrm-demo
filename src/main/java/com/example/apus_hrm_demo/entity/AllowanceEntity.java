package com.example.apus_hrm_demo.entity;

import com.example.apus_hrm_demo.util.DeductionType;
import com.example.apus_hrm_demo.util.AllowanceRewardType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name ="allowance")
@Getter
@Setter
public class AllowanceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, name = "include_type")
    private String includeType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AllowanceRewardType type;

    @Column(name = "uom_id")
    private Long uomId;
    @Column(name = "currency_id")
    private Long currencyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_allowance_id")
    private GroupAllowanceEntity groupAllowance;

    private String description;
    @Column(nullable = false, name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "allowanceEntity",fetch = FetchType.LAZY)
    private Set<AllowancePolicyLineEntity> allowancePolicyLineEntities;
}
