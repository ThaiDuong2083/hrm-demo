package com.example.apus_hrm_demo.mapper.payroll_line;

import com.example.apus_hrm_demo.util.enum_util.PayrollLineType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayrollLineContext {
    private Long groupTargetId;
    private PayrollLineType payrollLineType;
}
