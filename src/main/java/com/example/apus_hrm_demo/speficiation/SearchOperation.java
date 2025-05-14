package com.example.apus_hrm_demo.speficiation;

public enum SearchOperation {
    EQUALITY,        // =
    NEGATION,        // !=
    GREATER_THAN,    // >
    LESS_THAN,       // <
    LIKE,            // LIKE '%value%'
    STARTS_WITH,     // LIKE 'value%'
    ENDS_WITH,       // LIKE '%value'
    CONTAINS,         // LIKE '%value%'
    MULTI_FIELD_CONTAINS
}
