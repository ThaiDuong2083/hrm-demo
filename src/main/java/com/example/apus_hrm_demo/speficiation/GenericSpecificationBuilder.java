package com.example.apus_hrm_demo.speficiation;

import com.example.apus_hrm_demo.util.enum_util.SearchOperation;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GenericSpecificationBuilder<T> {

    private final List<SpecSearchCriteria> criteriaList = new ArrayList<>();

    public void  with(String key, SearchOperation operation, Object value, boolean orPredicate) {
        criteriaList.add(new SpecSearchCriteria(key, operation, value, orPredicate));
    }

    public Specification<T> build() {
        if (criteriaList.isEmpty()) return null;

        Specification<T> result = new GenericSpecification<>(criteriaList.getFirst());

        for (int i = 1; i < criteriaList.size(); i++) {
            SpecSearchCriteria criteria = criteriaList.get(i);
            Specification<T> spec = new GenericSpecification<>(criteria);
            result = criteria.isOrPredicate() ? Specification.where(result).or(spec) : Specification.where(result).and(spec);
        }

        return result;
    }
}
