package com.example.apus_hrm_demo.model.payroll_line;

import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.util.enum_util.AmountItem;
import com.example.apus_hrm_demo.util.enum_util.PayrollLineType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayrollLineDTO {
    private Long id;
    private PayrollLineType type;
    private BaseDTO groupTarget;
    private BaseDTO target;
    private AmountItem amountItem;
    private Double amount;
    private Double taxableAmount;
    private Double insuranceAmount;
}
