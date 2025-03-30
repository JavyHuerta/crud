package com.javyhuerta.crud.persistence.repository.specification;

import com.javyhuerta.crud.model.projection.BookProjection;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookSpecification implements Specification<BookProjection> {

    private static final long serialVersionUID = 3813633697798262411L;

    private String q;
    private LocalDate publicationDate;
    private Boolean onlineAvailability;

    @Override
    public Predicate toPredicate(
            Root<BookProjection> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesOr = new ArrayList<>();
        List<Predicate> predicatesAnd = new ArrayList<>();

        if (StringUtils.hasText(q)) {
            predicatesOr.add(criteriaBuilder.like(root.get("title"), "%" + q + "%"));
        }

        if (publicationDate != null) {
            predicatesAnd.add(criteriaBuilder.equal(root.get("publicationDate"), publicationDate));
        }

        if (onlineAvailability != null) {
            predicatesAnd.add(criteriaBuilder.equal(root.get("onlineAvailability"), onlineAvailability));
        }

        if (!predicatesOr.isEmpty() && !predicatesAnd.isEmpty()) {
            Predicate predicateOr = criteriaBuilder.and(predicatesOr.toArray(new Predicate[0]));
            Predicate predicateAnd = criteriaBuilder.and(predicatesAnd.toArray(new Predicate[0]));

            return criteriaBuilder.and(predicateAnd, criteriaBuilder.or(predicateOr));
        }

        if (!predicatesOr.isEmpty()) {
            return criteriaBuilder.or(predicatesOr.toArray(new Predicate[0]));
        }

        if (!predicatesAnd.isEmpty()) {
            return criteriaBuilder.and(predicatesAnd.toArray(new Predicate[0]));
        }

        return null;
    }
}

