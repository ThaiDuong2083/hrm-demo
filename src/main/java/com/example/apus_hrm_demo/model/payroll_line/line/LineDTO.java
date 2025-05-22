package com.example.apus_hrm_demo.model.payroll_line.line;

import com.example.apus_hrm_demo.model.base.BaseDTO;
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
public class LineDTO {
    private Long id ;
    private String name ;
    private String code ;
    private Set<DeductionType> includeType;
    private BaseDTO uom;
    private BaseDTO currency;
    private BaseDTO group;
}
