package com.example.apus_hrm_demo.speficiation;

import com.example.apus_hrm_demo.util.enum_util.SearchOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SpecSearchCriteria {
    private String key;
    private SearchOperation operation;
    private Object value;
    private boolean orPredicate;  // true = OR, false = AND
}
