package com.javyhuerta.crud.persistence.repository;

import com.javyhuerta.crud.model.projection.AuthorProjection;
import com.javyhuerta.crud.persistence.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthorRepository
        extends JpaRepository<Author, Integer>, JpaSpecificationExecutor<Author> {

    /*
     * @Modifying
     *
     * @Query( "INSERT INTO Author (firstName,lastName,birthdate) VALUES " +
     * "(:#{#author.firstName}, :#{#author.lastName}, :#{#author.birthdate})")
     * Integer save(@Param("author") Author author);
     */

    @Modifying
    @Query("DELETE FROM Author a WHERE a.authorId = :authorId")
    void delete(@Param("authorId") Integer authorId);

    @Query(
            "SELECT a.authorId as authorId, a.firstName as firstName, a.lastName as lastName, "
                    + "a.firstName||' '||a.lastName as fullName, a.birthdate as birthdate "
                    + "FROM Author a WHERE a.authorId = :authorId")
    AuthorProjection findByUserId(@Param("authorId") Integer authorId);

    @Modifying
    @Query(
            "UPDATE Author a SET a.firstName=:#{#author.firstName}, a.lastName=:#{#author.lastName},"
                    + " a.birthdate=:#{#author.birthdate} WHERE a.authorId = :#{#author.authorId}")
    Integer update(@Param("author") Author author);
}

