package com.example.apus_hrm_demo.filter;

import com.example.apus_hrm_demo.speficiation.GenericSpecificationBuilder;
import com.example.apus_hrm_demo.util.enum_util.SearchOperation;

public class CheckPayrollFilter {
    private CheckPayrollFilter() {}

    public static <T> GenericSpecificationBuilder<T> check(PayrollFilter payrollFliter){
        GenericSpecificationBuilder<T> builder = new GenericSpecificationBuilder<>();
        if (payrollFliter.getCode() != null && !payrollFliter.getCode().isEmpty()) {
            builder.with("code", SearchOperation.CONTAINS, payrollFliter.getCode(), false);
        }
        if (payrollFliter.getCycle() !=null) {
            builder.with("cycle", SearchOperation.EQUALITY, payrollFliter.getCycle(), false);
        }
        if (payrollFliter.getDepartmentId() !=null) {
            builder.with("departmentId", SearchOperation.EQUALITY, payrollFliter.getDepartmentId(), false);
        }
        if (payrollFliter.getPositionId() !=null) {
            builder.with("positionId", SearchOperation.EQUALITY, payrollFliter.getPositionId(), false);
        }
        return builder;
    }
}
