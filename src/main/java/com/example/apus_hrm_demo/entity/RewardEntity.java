package com.example.apus_hrm_demo.entity;

import com.example.apus_hrm_demo.util.enum_util.AllowanceRewardType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="reward")
@Getter
@Setter
@NoArgsConstructor
public class RewardEntity extends BaseEntity {
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

    @Column(name = "group_reward_id")
    private Long groupRewardId;

    private String description;

    public RewardEntity(Long id, Long groupRewardId) {
        this.id = id;
        this.groupRewardId = groupRewardId;
    }
}
