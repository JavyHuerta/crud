package com.javyhuerta.crud.persistence.dao;

import java.util.List;

import com.javyhuerta.crud.exception.ApiException;
import com.javyhuerta.crud.model.projection.BookProjection;
import com.javyhuerta.crud.persistence.repository.specification.BookSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface BookDao {

    Page<BookProjection> findAllToPage(BookSpecification bookSpec, Pageable pageable)
            throws ApiException;

    List<BookProjection> findAllToList(BookSpecification bookSpec, Sort sort);
}

