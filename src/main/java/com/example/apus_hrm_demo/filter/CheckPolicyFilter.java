package com.example.apus_hrm_demo.filter;

import com.example.apus_hrm_demo.speficiation.GenericSpecificationBuilder;
import com.example.apus_hrm_demo.util.enum_util.SearchOperation;

public class CheckPolicyFilter {
    private CheckPolicyFilter() {}

    public static <T> GenericSpecificationBuilder<T> check(PolicyFliter policyFliter){
        GenericSpecificationBuilder<T> builder = new GenericSpecificationBuilder<>();
        if (policyFliter.getName() != null && !policyFliter.getName().isEmpty()) {
            builder.with("name,code", SearchOperation.MULTI_FIELD_CONTAINS, policyFliter.getName(), false);
        }
        if (policyFliter.getType() !=null) {
            builder.with("type", SearchOperation.EQUALITY, policyFliter.getType(), false);
        }
        if (policyFliter.getState() !=null) {
            builder.with("state", SearchOperation.EQUALITY, policyFliter.getState(), false);
        }
        if (policyFliter.getStartDate() !=null) {
            builder.with("startDate", SearchOperation.GREATER_THAN, policyFliter.getStartDate(), false);
        }
        if (policyFliter.getEndDate() !=null) {
            builder.with("endDate", SearchOperation.LESS_THAN, policyFliter.getEndDate(), false);
        }
        if (policyFliter.getApplicableType() !=null) {
            builder.with("applicable_type", SearchOperation.EQUALITY, policyFliter.getApplicableType(), false);
        }
        return builder;
    }
}
