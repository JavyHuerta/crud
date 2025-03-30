package com.javyhuerta.crud.persistence.repository;

import java.util.Optional;

import com.javyhuerta.crud.model.projection.BookProjection;
import com.javyhuerta.crud.persistence.entity.BookAuthor;
import com.javyhuerta.crud.persistence.entity.BookAuthorId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BookAuthorRepository extends CrudRepository<BookAuthor, BookAuthorId> {

    @Query(
            value =
                    "SELECT b.book_id as bookId, b.title as title, b.publication_date as publicationDate,"
                            + " b.online_availability as onlineAvailability, GROUP_CONCAT(a.first_name||'"
                            + " '||a.last_name SEPARATOR ', ') as concatAuthors FROM book_authors ba INNER JOIN"
                            + " books b ON ba.book_id = b.book_id INNER JOIN authors a ON ba.author_id ="
                            + " a.author_id WHERE b.book_id = :bookId GROUP BY b.book_id",
            nativeQuery = true)
    Optional<BookProjection> findByBookId(@Param("bookId") Integer bookId);

    @Modifying
    @Query("DELETE FROM BookAuthor ba WHERE ba.bookAuthorId.bookId.bookId = :bookId")
    Integer deleteByBookId(@Param("bookId") Integer bookId);

    @Query(
            "SELECT CASE WHEN COUNT(ba.bookAuthorId.bookId.bookId)>0 THEN true ELSE false END FROM"
                    + " BookAuthor ba WHERE ba.bookAuthorId.bookId.bookId = :bookId")
    Boolean existByBookId(@Param("bookId") Integer bookId);

    @Query(
            "SELECT CASE WHEN COUNT(ba.bookAuthorId.authorId.authorId)>0 THEN true ELSE false END FROM"
                    + " BookAuthor ba WHERE ba.bookAuthorId.authorId.authorId = :authorId")
    Boolean existByAuthorId(@Param("authorId") Integer authorId);

    @Modifying
    @Query("DELETE FROM BookAuthor ba WHERE ba.bookAuthorId.authorId.authorId = :authorId")
    Integer deleteByAuthorId(@Param("authorId") Integer authorId);
}

