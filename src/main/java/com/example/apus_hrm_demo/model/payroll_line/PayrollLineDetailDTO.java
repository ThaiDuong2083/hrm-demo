package com.example.apus_hrm_demo.model.payroll_line;

import com.example.apus_hrm_demo.model.payroll_line.line.LineDTO;
import com.example.apus_hrm_demo.util.enum_util.AmountItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayrollLineDetailDTO {
    private Long id;
    private LineDTO line;
    private Double amount;
    private AmountItem amountItem;
    private Double taxableAmount;
    private Double insuranceAmount;
}
