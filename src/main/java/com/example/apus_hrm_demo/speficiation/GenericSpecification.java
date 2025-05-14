package com.example.apus_hrm_demo.speficiation;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GenericSpecification<T> implements Specification<T> {

    private final SpecSearchCriteria criteria;

    public GenericSpecification(SpecSearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        switch (criteria.getOperation()) {
            case EQUALITY:
                return cb.equal(root.get(criteria.getKey()), criteria.getValue());
            case NEGATION:
                return cb.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN:
                return cb.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN:
                return cb.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LIKE:
                return cb.like(cb.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%");
            case STARTS_WITH:
                return cb.like(cb.lower(root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase() + "%");
            case ENDS_WITH:
                return cb.like(cb.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase());
            case CONTAINS:
                return cb.like(cb.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%");
            case MULTI_FIELD_CONTAINS:
                String[] fields = criteria.getKey().split(",");
                List<Predicate> orPredicates = new ArrayList<>();
                for (String field : fields) {
                    orPredicates.add(cb.like(cb.lower(root.get(field.trim())),
                            "%" + criteria.getValue().toString().toLowerCase() + "%"));
                }
                return cb.or(orPredicates.toArray(new Predicate[0]));
            default:
                return null;
        }
    }
}
