package com.example.apus_hrm_demo.model.allowance;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.util.enum_util.AllowanceRewardType;
import com.example.apus_hrm_demo.util.enum_util.DeductionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllowanceForPolicyLineDTO  {
    private Long id;
    private String name;
    private String code;
    private Set<DeductionType> includeType;
    private BaseDTO groupAllowance;
    private BaseDTO currency;
    private BaseDTO uom;
    private AllowanceRewardType type;
}
