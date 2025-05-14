package com.example.apus_hrm_demo.model.reward;

import com.example.apus_hrm_demo.model.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardGetAllDTO {
    private Long id;
    private String name;
    private String code;
    private BaseDTO groupReward;
    private String description;
    private Boolean isActive;
}
