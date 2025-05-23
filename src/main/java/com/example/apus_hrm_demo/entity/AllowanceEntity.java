package com.example.apus_hrm_demo.entity;

import com.example.apus_hrm_demo.util.enum_util.AllowanceRewardType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="allowance")
@Getter
@Setter
@NoArgsConstructor
public class AllowanceEntity extends BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private String includeType;

    @Enumerated(EnumType.STRING)
    private AllowanceRewardType type;

    private Long uomId;

    private Long currencyId;

    private Long groupAllowanceId;

    private String description;
    private Boolean isActive;
}
