package com.javyhuerta.crud.persistence.repository;

import com.javyhuerta.crud.model.projection.BookProjection;
import com.javyhuerta.crud.persistence.dao.BookDao;
import com.javyhuerta.crud.persistence.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository
        extends JpaRepository<Book, Integer>,
        // CrudRepository<Book, Integer>,
        // PagingAndSortingRepository<Book, Integer>,
        JpaSpecificationExecutor<BookProjection>,
        BookDao {

}
