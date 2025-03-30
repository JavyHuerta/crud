package com.javyhuerta.crud.persistence.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookAuthorId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book bookId;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author authorId;
}

