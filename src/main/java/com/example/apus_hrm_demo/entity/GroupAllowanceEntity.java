package com.example.apus_hrm_demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "group_allowance")
@Getter
@Setter
public class GroupAllowanceEntity extends BaseEntity {
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

    @Column(nullable = false, name = "is_active")
    private Boolean isActive;
}
