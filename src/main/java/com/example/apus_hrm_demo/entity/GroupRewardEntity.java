package com.example.apus_hrm_demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "group_reward")
@Getter
@Setter
public class GroupRewardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(name = "parent_id")
    private Long parentId;

    private String description;

    @OneToMany(mappedBy = "groupReward",fetch = FetchType.LAZY)
    private Set<RewardEntity> rewardEntities;
}
